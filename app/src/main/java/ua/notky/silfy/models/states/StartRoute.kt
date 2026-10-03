package ua.notky.silfy.models.states

/** Where the app goes after the splash */
enum class StartRoute {
    /** Active profile exists → dictionary */
    MAIN,

    /** Very first launch, no profiles yet */
    WELCOME,

    /** "Who's learning?" (opens "Create profile" right away when there are no profiles) */
    PROFILES
}
