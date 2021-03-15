package com.lmx1.enhancedpollution.Commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class PollutionCommand extends CommandBase
{
    private final List<String> aliases;
    private final List<String> subs = new ArrayList<>();

    public PollutionCommand()
    {
        aliases = new ArrayList<>();
        aliases.add("pollution");
        subs.add("set");
        subs.add("add");
        subs.add("rgb");
    }

    @Override
    public int compareTo(Object o)
    {
        return 0;
    }

    @Override
    public String getCommandName()
    {
        return "pollution";
    }

    @Override
    public String getCommandUsage(ICommandSender var1)
    {
        return "pollution <subcommand> <pollution> [range]";
    }

    @Override
    public List getCommandAliases()
    {
        return this.aliases;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args)
    {
        World world = sender.getEntityWorld();
        //Make sure it's only executed server side
        if(!world.isRemote)
        {
            try {
                String sub = args[0];
                if(!subs.contains(sub)){
                    sender.addChatMessage(new ChatComponentText( "Error parsing command! Usage: pollution <subcommand> <pollution> [radius]"));
                    return;
                }
                if(!sub.equals("rgb")) {
                    int pollution = Integer.parseInt(args[1]);
                    boolean result = CommandUtils.changePollution(world, sender.getPlayerCoordinates(), args.length > 2 ? Integer.parseInt(args[2]) : 0, pollution, sub.equals("set"));
                    sender.addChatMessage(new ChatComponentText(result ? "Changed Pollution for selected chunks" : "Error in command"));
                }
                else{
                    int r = Integer.parseInt(args[1]);
                    int g = Integer.parseInt(args[2]);
                    int b = Integer.parseInt(args[3]);
                    boolean result = CommandUtils.setRGB(r,g,b);
                    sender.addChatMessage(new ChatComponentText(result ? "Changed RGB for fog" : "Error in command"));
                }
            } catch (NumberFormatException e) {
                sender.addChatMessage(new ChatComponentText( "Error parsing pollution! Usage: pollution <subcommand> <pollution> [radius]"));
            }
        }
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender var1)
    {
        return true;
    }

    @Override
    public List addTabCompletionOptions(ICommandSender var1, String[] var2)
    {
        return null;
    }

    @Override
    public boolean isUsernameIndex(String[] var1, int var2)
    {
        return false;
    }
}