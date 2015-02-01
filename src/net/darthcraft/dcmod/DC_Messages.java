package net.darthcraft.dcmod;

import org.bukkit.ChatColor;

public class DC_Messages
{
    // This wont really do anything more than replace exsisting messages for the time being. You can ignore it for the most part, but hey ho. Its here to make my life easier in th elonger run. 

    //TODO: Group These into something decently normal ish...
    //TODO: Actually alphabetize these to make them easily readable, because this is just a pain to read through..
    public static final String PLAYER_NOT_FOUND = "The player you have entered cannot be found. Please check that the spelling is correct.";
    public static final String ERROR = ChatColor.DARK_RED + "There has been an error somewhere. Please contact a devleoper!";
    public static final String CANNOT_BAN_PLAYER = ChatColor.DARK_RED + "The player you have listed cannot be banned. Please contact a host.";
    public static final String SPECIFY_REASON = ChatColor.DARK_RED + "You have not specificed a valid reason. Please re-check your command";
    public static final String ALREADY_BANNED = ChatColor.DARK_RED + "The player has already been banned - Sorry :)";
    public static final String MESSAGE_SET = ChatColor.DARK_GREEN + "The login message that you have entered has been set. Your message is: ";
    public static final String MESSAGE_REMOVED = ChatColor.DARK_GREEN + "Your login message has been removed. You will no longer have one when you join the server.";
    public static final String CANNOT_WARN_PLAYER = ChatColor.DARK_RED + "You may not warn this player. Please contact a host.";
    public static final String WORLD_IMPLODE = ChatColor.DARK_RED + "AHHHHHHH STOP - By trying to do this you will cause the world to implode. Please check what you are trying to do before the end of the world comes. Also, you should construct additional pylons...";
    public static final String NO_PREMS = ChatColor.DARK_RED + "It appears that you do not have permissions to execute this command. If you have any qyestions please contact an admin or higher!";
    public static final String WARNINGS_AS_FOLLOWS = ChatColor.DARK_GREEN + "Your warning reasons are as follows with the amount of points issues in brackets.";
    public static final String WAIVE_WARNINGS_REASONS = ChatColor.DARK_GREEN + "Your waived warning reasons are as follows with the amount waived in brackets.";
    public static final String OTHER_WARNING_AMOUNT = ChatColor.DARK_PURPLE + " has the following amount of warnings linked to their acount. ";
    public static final String WARNING_REASONS = ChatColor.DARK_AQUA + "You have the following amount of warning points linked to your account: ";
    public static final String CANNOT_KICK_PLAYER = ChatColor.DARK_RED + "You cannot kick this player. Please contact a host.";
    public static final String YOU_BEEN_KICKED = "You have bene kicked from the server with the following reason:";
    public static final String YOU_LIKED = "You have liked DarthCraft. Thank you!";
    public static final String NOT_LIKED = "You have yet to like the server :( Please head to the spawn to hit the like sign.";
    public static final String MUST_LOOK = "You must be looking at a sign to make this work... You Dun goofed.";
    public static final String ALREADY_LIKE_SIGN = "While I appreciate your mission to get as many likes as possible, this sign is alreayd a like sign...";
    public static final String LIKESIGN_SET = "The like sign has been set!";
    public static final String LIKESIGN_REMOVED = "This likesign has been removed. WHY YOU DO DIS?!?!?!?!?";
    public static final String NOT_LIKESIGN = "This is not actually a likesign...";
    public static final String CANNOT_MUTE_PLAYER = ChatColor.DARK_RED + "You cannot mute this player... Abort mission.";
    public static final String ALREADY_MUTED = ChatColor.DARK_RED + "Sorry... That player is already muted. Please /unmute them if thats what you were actually trying to do.";
    public static final String MUTING = "Muting ";
    public static final String IGN_ONLY = ChatColor.DARK_RED + "Sorry... You kinda need to get off this console and get in game to do this... Your loss.";
    public static final String REPORT_REASON = " has reported the following player with the following reason: ";
    public static final String REPORTEE = "You have had a report filed against you. A server administrator will review this soon. The reason is as follows: ";
    public static final String REPORT = "The report has now been filed. An admin will be reviewing it for you soon.";
    public static final String RANK_SET = "'s rank has been set to ";
    public static final String XP_LOW = "It appears that your XP is a tad too low... Ahh well, try again another day.";
    public static final String NEW_XP = "Your new XP level is: ";
    public static final String INVALID_DATE = ChatColor.DARK_RED + "You have entered an invalid expiry date.";
    public static final String PLAYER_NOT_BANNED = ChatColor.DARK_RED + "The player is not currently banned.";
    public static final String IP_NOT_BANNED = ChatColor.DARK_RED + "The IP you have entered is not currently banned";
    public static final String UNMUTED = ChatColor.DARK_GREEN + "The player is not actually muted...";
    public static final String NO_MORE_THAN_TEN = ChatColor.DARK_RED + "You are unable to issue greater than 10 points. Please contact a host or higher if you feel it appropriate to issue this amount.";
    public static final String CANNOT_REMOVE_POINTS = ChatColor.DARK_RED + "To remove warning points you will need to contact a head admin or host.";
    public static final String WARNING_BROADCAST_MESSAGE = " - Issuing a warning to the following player with the warning reason.";
    public static final String CONSOLE_ONLY = "This command may only be executed via the console.";
    public static final String PLAYER_ONLY = "This command may only be executed through the console.";
    public static final String NO_COMMAND_PERMS = "Command {0} doesn''t have permissions set!";
    public static final String NO_COMMAND_SOURCE = "Command {0} doesn''t have a command-source set!";
    public static final String MYSQL_NOT_ENABLED = "MySQL has not been started. Please chcek your config to ensure you have enabled it";
    public static final String PLUGIN_ENABLED = "Version {0} by {1} is enabled";
    public static final String PLUGIN_DISABLED = "Version {0} is disabled";
    public static final String COMMAND_NOT_LOADED = ChatColor.RED + "Command Error: Command not loaded: ";
    public static final String COMMAND_NOT_LOADED_LOG = "Command not loaded: {0}";
    public static final String UNKOWN_COMMAND_ERROR = "Unknown command error: {0}";
    public static final String COMMAND_ERROR = "Command Error: ";
    public static final String MYSQL_ENABLED = "Success - MySQL connection has been established";
    public static final String DISABLE_PLUGIN = "DarthCraftMod disabling as defined in the config.";
    public static final String DEBUG_ENABLED = "Debug mode has been enabled - Please note that debug logs will be kept in english with no supported translations at the current time.";
    public static final String CANNOT_LOAD_BUILD_INFO = "Could not load build information!";
    public static final String ABDUCT_HAMMER = ChatColor.BLUE + "Unleash the power of..." + ChatColor.YELLOW + "Abducting Players!";
    public static final String BUSY_ON = "Busy status has been toggled on.";
    public static final String BUSY_OFF = "Busy status has been toggled off.";
    public static final String CURRENTLY_BUSY = " is currently busy. Please try them again later and do not pester.";
    public static final String CANNOT_LOAD_BANS = "Could not load player ban: {0}!";
    public static final String BANS_MESSAGE = " {0} bans";
    public static final String LOADED = "Loaded";
    public static final String SAVED = "Saved";
    public static final String BAN_GET_ERROR = "Sorry! " + ChatColor.AQUA + "There was a problem checking if you were banned.\n";
    public static final String APPEALAT_MESSAGE = "Please report this issue at";
    public static final String CANNOT_CHECK_BANS = "Problem checking ban status for {0}";
    public static final String REMOVE_OLD_BANS = "Removing expired ban: {0}";
    public static final String PLAYER_ALREADY_BANNED = "Not banning {0}: already banned!";
    public static final String BM_PLAYER_NOT_BANNED = "Not unbanning {0}: not banned!";
}
