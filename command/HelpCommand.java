package fredboat.commons.command;

import fredboat.commons.commandmeta.Command;
import fredboat.commons.commandmeta.IBackupCommand;
import fredboat.commons.util.CommonConstants;
import net.dv8tion.jda.entities.Guild;
import net.dv8tion.jda.entities.Message;
import net.dv8tion.jda.entities.TextChannel;
import net.dv8tion.jda.entities.User;

public class HelpCommand extends Command implements IBackupCommand {
    
    @Override
    public void onInvoke(Guild guild, TextChannel channel, User invoker, Message message, String[] args) {
        invoker.getPrivateChannel().sendMessage(CommonConstants.HELP_TEXT);
        channel.sendMessage(invoker.getUsername() + ": Documentation has been sent to your direct messages!");
    }
    
}
