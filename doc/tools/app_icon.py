"""Silfy 2.0 app icon 7a: "s" (Unbounded Bold) + dot, ink on aqua.

Generates from app/src/main/res/font/unbounded_bold.ttf (no extra libraries for the vector part):
  - drawable/ic_launcher_foreground.xml  — adaptive icon foreground, monochrome layer, Android 12+ splash icon
  - mipmap-*/ic_launcher.png, ic_launcher_round.png — legacy icons for API 24–25 (needs Pillow)

Geometry comes from the design's play-store/icon-512.png: on the 512 canvas the "s" occupies
x 145..365, y 170..361 and the dot is a circle at (376.5, 135.5), r 33.5. The 512 canvas maps to the
visible 72dp of the 108dp adaptive layer (offset 18dp), so everything stays in the 66dp safe zone.

Run: python doc/tools/app_icon.py
"""
import os
import struct

ROOT = os.path.join(os.path.dirname(os.path.abspath(__file__)), '..', '..')
RES = os.path.join(ROOT, 'app', 'src', 'main', 'res')
FONT = os.path.join(RES, 'font', 'unbounded_bold.ttf')

INK = '#FF100C4E'
AQUA = (0x72, 0xE4, 0xEC)
INK_RGB = (0x10, 0x0C, 0x4E)

# Design geometry on the 512 canvas
S_BOX = (145.0, 170.0, 365.0, 361.0)
DOT = (376.5, 135.5, 33.5)


# ---------- minimal TrueType reader (cmap 4 / 12, loca, simple glyf) ----------

class Font:
    def __init__(self, path):
        self.data = open(path, 'rb').read()
        num = struct.unpack('>H', self.data[4:6])[0]
        self.tables = {}
        for i in range(num):
            tag, _, off, length = struct.unpack('>4sIII', self.data[12 + 16 * i:28 + 16 * i])
            self.tables[tag.decode()] = (off, length)

    def table(self, tag):
        off, length = self.tables[tag]
        return self.data[off:off + length]

    def glyph_id(self, char):
        cmap = self.table('cmap')
        code = ord(char)
        num = struct.unpack('>H', cmap[2:4])[0]
        for i in range(num):
            platform, encoding, off = struct.unpack('>HHI', cmap[4 + 8 * i:12 + 8 * i])
            fmt = struct.unpack('>H', cmap[off:off + 2])[0]
            if fmt == 4:
                seg2 = struct.unpack('>H', cmap[off + 6:off + 8])[0]
                ends = struct.unpack('>%dH' % (seg2 // 2), cmap[off + 14:off + 14 + seg2])
                starts_off = off + 16 + seg2
                starts = struct.unpack('>%dH' % (seg2 // 2), cmap[starts_off:starts_off + seg2])
                deltas = struct.unpack('>%dh' % (seg2 // 2), cmap[starts_off + seg2:starts_off + 2 * seg2])
                ranges_off = starts_off + 2 * seg2
                ranges = struct.unpack('>%dH' % (seg2 // 2), cmap[ranges_off:ranges_off + seg2])
                for s in range(seg2 // 2):
                    if starts[s] <= code <= ends[s]:
                        if ranges[s] == 0:
                            return (code + deltas[s]) & 0xFFFF
                        addr = ranges_off + 2 * s + ranges[s] + 2 * (code - starts[s])
                        gid = struct.unpack('>H', cmap[addr:addr + 2])[0]
                        return (gid + deltas[s]) & 0xFFFF if gid else 0
            elif fmt == 12:
                groups = struct.unpack('>I', cmap[off + 12:off + 16])[0]
                for g in range(groups):
                    start, end, gid = struct.unpack('>III', cmap[off + 16 + 12 * g:off + 28 + 12 * g])
                    if start <= code <= end:
                        return gid + code - start
        raise KeyError(char)

    def contours(self, gid):
        """Contours as lists of (x, y, on_curve) in font units"""
        long_loca = struct.unpack('>h', self.table('head')[50:52])[0] == 1
        loca = self.table('loca')
        if long_loca:
            start, end = struct.unpack('>II', loca[4 * gid:4 * gid + 8])
        else:
            start, end = [v * 2 for v in struct.unpack('>HH', loca[2 * gid:2 * gid + 4])]
        g = self.table('glyf')[start:end]
        n_contours = struct.unpack('>h', g[0:2])[0]
        if n_contours < 0:
            raise ValueError('composite glyph is not supported')
        end_pts = struct.unpack('>%dH' % n_contours, g[10:10 + 2 * n_contours])
        n_points = end_pts[-1] + 1
        pos = 10 + 2 * n_contours
        ins_len = struct.unpack('>H', g[pos:pos + 2])[0]
        pos += 2 + ins_len

        flags = []
        while len(flags) < n_points:
            flag = g[pos]
            pos += 1
            flags.append(flag)
            if flag & 8:
                repeat = g[pos]
                pos += 1
                flags.extend([flag] * repeat)

        def coords(short_bit, same_bit):
            nonlocal pos
            values, value = [], 0
            for flag in flags:
                if flag & short_bit:
                    delta = g[pos]
                    pos += 1
                    value += delta if flag & same_bit else -delta
                elif not flag & same_bit:
                    value += struct.unpack('>h', g[pos:pos + 2])[0]
                    pos += 2
                values.append(value)
            return values

        xs = coords(2, 16)
        ys = coords(4, 32)
        result, first = [], 0
        for last in end_pts:
            result.append([(xs[i], ys[i], bool(flags[i] & 1)) for i in range(first, last + 1)])
            first = last + 1
        return result


def contour_segments(points):
    """TrueType quadratic contour → list of ('M'|'L'|'Q', points) with implied on-curve points"""
    n = len(points)
    start = next((i for i, p in enumerate(points) if p[2]), None)
    if start is None:  # all off-curve: start at the midpoint of the first two
        a, b = points[0], points[1]
        pts = [((a[0] + b[0]) / 2, (a[1] + b[1]) / 2, True)] + points[1:] + points[:1]
    else:
        pts = points[start:] + points[:start]
    cmds = [('M', [pts[0][:2]])]
    i = 1
    pts = pts + [pts[0]]
    while i < len(pts):
        p = pts[i]
        if p[2]:
            cmds.append(('L', [p[:2]]))
            i += 1
        else:
            nxt = pts[i + 1] if i + 1 < len(pts) else pts[0]
            if nxt[2]:
                cmds.append(('Q', [p[:2], nxt[:2]]))
                i += 2
            else:
                mid = ((p[0] + nxt[0]) / 2, (p[1] + nxt[1]) / 2)
                cmds.append(('Q', [p[:2], mid]))
                i += 1
    return cmds


def glyph_commands(font, char):
    return [contour_segments(c) for c in font.contours(font.glyph_id(char))]


def bounds(contours):
    xs = [pt[0] for c in contours for _, pts in c for pt in pts]
    ys = [pt[1] for c in contours for _, pts in c for pt in pts]
    return min(xs), min(ys), max(xs), max(ys)


def fit(contours, box):
    """Font units (y up) → box (x0, y0, x1, y1) in canvas units (y down), keeping the aspect by width"""
    fx0, fy0, fx1, fy1 = bounds(contours)
    scale = (box[2] - box[0]) / (fx1 - fx0)
    height = (fy1 - fy0) * scale
    y_top = box[1] + ((box[3] - box[1]) - height) / 2
    return lambda x, y: (box[0] + (x - fx0) * scale, y_top + (fy1 - y) * scale)


def to_canvas(v, size, offset):
    return offset + v * size / 512.0


def path_data(contours, transform):
    out = []
    for c in contours:
        for cmd, pts in c:
            coords = ' '.join('%.2f,%.2f' % transform(*p) for p in pts)
            out.append('%s%s' % (cmd, coords))
        out.append('Z')
    return ' '.join(out)


def dot_path(cx, cy, r):
    return 'M%.2f,%.2f m-%.2f,0 a%.2f,%.2f 0,1 1,%.2f,0 a%.2f,%.2f 0,1 1,-%.2f,0 Z' % (
        cx, cy, r, r, r, 2 * r, r, r, 2 * r)


def glyph_transform(contours, size, offset):
    box = tuple(to_canvas(v, size, offset) for v in S_BOX)
    return fit(contours, box)


def vector(contours, size, offset, comment):
    t = glyph_transform(contours, size, offset)
    cx, cy, r = (to_canvas(DOT[0], size, offset), to_canvas(DOT[1], size, offset), DOT[2] * size / 512.0)
    return '''<?xml version="1.0" encoding="utf-8"?>
<!--  %s
      Generated by doc/tools/app_icon.py — edit the script, not this file  -->
<vector xmlns:android="http://schemas.android.com/apk/res/android"
    android:width="108dp"
    android:height="108dp"
    android:viewportWidth="108"
    android:viewportHeight="108">
    <path
        android:fillColor="%s"
        android:pathData="%s" />
    <path
        android:fillColor="%s"
        android:pathData="%s" />
</vector>
''' % (comment, INK, path_data(contours, t), INK, dot_path(cx, cy, r))


# ---------- legacy PNG (API 24–25) ----------

LEGACY = {'mdpi': 48, 'hdpi': 72, 'xhdpi': 96, 'xxhdpi': 144, 'xxxhdpi': 192}


def flatten(contours, transform, steps=12):
    polys = []
    for c in contours:
        poly, cur = [], None
        for cmd, pts in c:
            if cmd in ('M', 'L'):
                cur = transform(*pts[0])
                poly.append(cur)
            else:
                (cx, cy), end = transform(*pts[0]), transform(*pts[1])
                for k in range(1, steps + 1):
                    t = k / steps
                    x = (1 - t) ** 2 * cur[0] + 2 * (1 - t) * t * cx + t * t * end[0]
                    y = (1 - t) ** 2 * cur[1] + 2 * (1 - t) * t * cy + t * t * end[1]
                    poly.append((x, y))
                cur = end
        polys.append(poly)
    return polys


def legacy_png(contours, px, round_shape):
    from PIL import Image, ImageDraw
    ss = 8  # supersampling
    size = px * ss
    # Legacy icon: 48dp with a 1dp margin; the 512 canvas fills the 46dp shape
    margin = size / 48.0
    shape = size - 2 * margin
    img = Image.new('RGBA', (size, size), (0, 0, 0, 0))
    draw = ImageDraw.Draw(img)
    box = [margin, margin, margin + shape, margin + shape]
    if round_shape:
        draw.ellipse(box, fill=AQUA + (255,))
    else:
        draw.rounded_rectangle(box, radius=shape * 0.22, fill=AQUA + (255,))

    # Glyph: even-odd fill of all contours (the "s" has no holes)
    mask = Image.new('L', (size, size), 0)
    mdraw = ImageDraw.Draw(mask)
    t = glyph_transform(contours, shape, margin)
    for poly in flatten(contours, t):
        mdraw.polygon(poly, fill=255)
    cx, cy, r = (to_canvas(DOT[0], shape, margin), to_canvas(DOT[1], shape, margin), DOT[2] * shape / 512.0)
    mdraw.ellipse([cx - r, cy - r, cx + r, cy + r], fill=255)
    img.paste(Image.new('RGBA', (size, size), INK_RGB + (255,)), (0, 0), mask)
    return img.resize((px, px), Image.LANCZOS)


def main():
    font = Font(FONT)
    contours = glyph_commands(font, 's')

    # Adaptive layer: the 512 canvas → visible 72dp in the middle of 108dp
    files = {
        os.path.join(RES, 'drawable', 'ic_launcher_foreground.xml'):
            vector(contours, 72, 18, 'App icon 7a foreground (also the monochrome layer and the Android 12+ splash icon): "s" + dot, 108dp'),
    }
    for path, text in files.items():
        with open(path, 'w', encoding='utf-8', newline='\n') as f:
            f.write(text)

    try:
        for density, px in LEGACY.items():
            folder = os.path.join(RES, 'mipmap-' + density)
            os.makedirs(folder, exist_ok=True)
            legacy_png(contours, px, False).save(os.path.join(folder, 'ic_launcher.png'))
            legacy_png(contours, px, True).save(os.path.join(folder, 'ic_launcher_round.png'))
        print('vectors + legacy PNGs written')
    except ImportError:
        print('vectors written; legacy PNGs need Pillow')


if __name__ == '__main__':
    main()
