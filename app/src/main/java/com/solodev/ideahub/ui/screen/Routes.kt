package com.solodev.ideahub.ui.screen

enum class Routes {
    Home,
    Login,
    SignUp,
    MailConfirmation,
    Community,
    Gemini,
    Profile,
    Thread,
    ThreadHistory,
    UserThreadScreen,
    GoalCreation,
    Welcome,
    CreateGroup,
    ConfirmCreateGoal;


    fun withArgs(vararg args: String): String {
        return this.name + args.joinToString(separator = "/", prefix = "/")
    }
}