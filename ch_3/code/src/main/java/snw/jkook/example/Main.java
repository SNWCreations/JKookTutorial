package snw.jkook.example;

import snw.jkook.command.JKookCommand;
import snw.jkook.entity.User;
import snw.jkook.message.component.MarkdownComponent;
import snw.jkook.message.component.TextComponent;
import snw.jkook.plugin.BasePlugin;

public class Main extends BasePlugin {

    @Override
    public void onEnable() {
        // "eg" is the root name of the command.
        // In this example, the command prefix is "/" (default value)
        // So you can execute this command by sending a message which the content is "/eg" in KOOK.
        new JKookCommand("eg")
                // You can add another string as the new prefix of this command.
                // This method can be called for many times.
                // The only restriction is that the provided string cannot have space characters.
                .addPrefix(".")
                // You can add an alias for this command.
                // This method can be called for many times.
                // The restriction is the same as addPrefix method. (No space character in the provided string)
                .addAlias("example")
                // Actually, the /help command is provided by JKook API implementations.
                // So it's possible to make the description never be shown for some API implementations.
                // This rule is also applicable to the help content of the commands.
                .setDescription("The description will be shown in the result of /help command")
                .setHelpContent("The help content will be shown in the result of '/help eg' command")
                // This method is the only way to set the executor in JKook API 0.37 and before.
                // The sender type is CommandSender, so it might be a User or ConsoleCommandSender.
                // So you need to write (if-else + instanceof) statements to know the sender.
                // For users, you need to cast them into User manually.
                // so we added executeUser and executeConsole to prevent this problem in API 0.38
                // Tips: The CommandExecutor won't be executed if the command have a UserCommandExecutor
                // and the executor is User, or the command have a ConsoleCommandExecutor and the sender is
                // ConsoleCommandSender.
                // This is just for the backwards compatibility.
                // Actually, it is deprecated.
                .setExecutor((sender, arguments, message) -> {
                    // the commands can be invoked by using CommandManager#executeCommand.
                    // That method will invoke the command with sender and arguments, but no message object provided.
                    // So if you don't check the message, a NullPointerException will be thrown.
                    if (sender instanceof User && message != null) {
                        message.reply(new MarkdownComponent("Hi!"));
                    }
                })
                // The better way to set the command executor for users since JKook 0.38
                .executesUser((sender, arguments, message) -> {
                    if (message != null) {
                        message.reply(new TextComponent("Hi!"));
                    }
                })
                .executesConsole((sender, arguments) -> {
                    // console command executors cannot be triggered through KOOK application.
                    // so there is no message object available.
                    this.getLogger().info("Hi!");
                })
                .register(this); // DO NOT FORGET TO REGISTER COMMAND AT THE END OF CALL CHAIN!
                // .register() // If you are using JKook API 0.37 (or before), you don't need to provide
                // Plugin instance to the register method.
                //
                // In JKook API 0.37 and before, we don't need a Plugin instance for registering the command
                // But it is not good for the command management (e.g. unregister command when disabling plugins)
                // So I made a breaking change in API 0.38, plugin developers need to provide a Plugin instance
                // to register the command since that commit
                //
                // WARNING: THE ATTRIBUTES OF THE COMMAND CANNOT BE EDITED AFTER IT REGISTERED.

        new JKookCommand("greet")
                // You can add many subcommands by using addSubcommand method.
                .addSubcommand(
                        new JKookCommand("name")
                                .executesUser((sender, arguments, message) -> {
                                    if (message == null) {
                                        return; // Invoked by using CommandManager#executeCommand
                                                // You can halt the command execution if you want
                                    }
                                    if (arguments.length == 0) {
                                        message.reply(new MarkdownComponent("No name?"));
                                    }
                                    // In JKook API 0.37 (and before), the result of arguments[0] is a String.
                                    // But in JKook API 0.38+, the result is Object.
                                    // Why? Because API 0.38 added the argument parser system,
                                    // You can specify the argument type by using JKookCommand#addArgument.
                                    message.reply(new MarkdownComponent("Hi! " + arguments[0]));
                                })
                )
                .register(this);

        new JKookCommand("arg")
                // You can specify the command argument type by using addArgument method.
                // Java standard data types are supported by default.
                .addArgument(String.class)
                .addArgument(int.class)
                // Optional argument is supported.
                // For example, in this command, if you execute "/arg SNWCreations 159",
                // the value of this argument is "play"
                // But if you execute "/arg SNWCreations 60 Sleep"
                // the value of this argument is "sleep"
                .addOptionalArgument(String.class, "play")
                // Also, you can specify custom type.
                // But the parser of the type that you provided is required before registering this command.
                .executesUser((sender, arguments, message) -> {
                    // I'm sure arguments.length is 3.
                    if (message == null) {
                        return;
                    }
                    // I'm sure the following cast will be succeeded
                    String name = (String) arguments[0];
                    int money = (int) arguments[1];
                    String action = (String) arguments[2];
                    message.reply(new MarkdownComponent(
                            String.format("%s has %s CNY.\nHis next action is %s.", name, money, action)
                    ));
                })
                .register(this);
    }
}
