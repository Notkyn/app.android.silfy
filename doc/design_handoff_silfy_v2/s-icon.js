(()=>{if(customElements.get('s-icon'))return;const cache={};
const base=new URL('icons/',document.currentScript?document.currentScript.src:location.href).href;
customElements.define('s-icon',class extends HTMLElement{static get observedAttributes(){return['name','style']}
connectedCallback(){this.draw()}
attributeChangedCallback(){if(this.isConnected)this.draw()}
async draw(){const n=this.getAttribute('name');if(!n||n.includes('{'))return;
const p=cache[n]||(cache[n]=fetch(base+n+'.svg').then(r=>r.text()));const t=await p;
const col=getComputedStyle(this).color;const key=n+'|'+col;if(this._k===key)return;this._k=key;
const svg=t.replace(/<!--[\s\S]*?-->/g,'').replace(/currentColor/g,col).replace(/\s+/g,' ');
this.style.backgroundImage=`url("data:image/svg+xml,${encodeURIComponent(svg)}")`;
this.style.backgroundSize='100% 100%';this.style.backgroundRepeat='no-repeat';if(!this.style.display)this.style.display='inline-block';}});})();
