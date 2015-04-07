package net.darthcraft.dcmod;

import org.bukkit.ChatColor;

public class DC_Messages
{
// Error messages

    public static final String PLAYER_NOT_FOUND = "The player you have entered cannot be found. Please check that the spelling is correct and the player is online.";
    public static final String ERROR = ChatColor.DARK_RED + "Someone forget to feed the developers and there is a bug or two somewhere in the mystical code!  Please post this on the forums!";
    public static final String CANNOT_BAN_PLAYER = ChatColor.DARK_RED + "The player you have entered cannot be banned!  Please contact a host, immediately!";
    public static final String NO_PREMS = ChatColor.DARK_RED + "It appears that you do not have permissions to execute this command. If this is an error, please contact any admin.";
    public static final String COMMAND_NOT_LOADED = ChatColor.RED + "Command Error: Command not loaded - Please contact a member of staff: ";
    public static final String COMMAND_NOT_LOADED_LOG = "Command not loaded: {0}";
    public static final String UNKOWN_COMMAND_ERROR = "Unknown command error: {0}";
    public static final String COMMAND_ERROR = "There has been a Command Error: ";
    public static final String MYSQL_ENABLED = "Success - MySQL connection has been successfully established!";
// Administrator related
    public static final String SPECIFY_REASON = ChatColor.DARK_RED + "You have not specified a valid reason.  Please, try again";
    public static final String ALREADY_BANNED = ChatColor.DARK_RED + "The player has already been banned - Sorry :)";
    public static final String CANNOT_KICK_PLAYER = ChatColor.DARK_RED + "You cannot kick this player. Please contact a host.";
    public static final String MUTING = "Muting ";
    public static final String CANNOT_MUTE_PLAYER = ChatColor.DARK_RED + "You are unable to mute the following user... Abort mission!";
    public static final String ALREADY_MUTED = ChatColor.DARK_RED + "This user is already muted!  If you are trying to remove the mute, use /unmute";
    public static final String INVALID_DATE = ChatColor.DARK_RED + "You have entered an invalid expiry date.  Try again..";
    public static final String PLAYER_NOT_BANNED = ChatColor.DARK_RED + "The player is not currently banned.";
    public static final String IP_NOT_BANNED = ChatColor.DARK_RED + "The IP you have entered is not currently banned";
    public static final String UNMUTED = ChatColor.DARK_GREEN + "The following user isn't muted.";
    public static final String BUSY_ON = "Busy status has been toggled on.";
    public static final String BUSY_OFF = "Busy status has been toggled off.";
    public static final String CURRENTLY_BUSY = " is currently busy!  Please, try them again later and do not pester!";
// Misc. 
    public static final String MESSAGE_SET = ChatColor.DARK_GREEN + "The login message that you have entered has been set. Your new message is: ";
    public static final String MESSAGE_REMOVED = ChatColor.DARK_GREEN + "Your login message has been removed. You do not have a login message when you join.";
    public static final String CANNOT_WARN_PLAYER = ChatColor.DARK_RED + "You may not warn this player.  If you believe this is an error, please contact a host.";
    public static final String WORLD_IMPLODE = ChatColor.DARK_RED + "AHHHHHHH STOP - By trying to do this you will cause the world to implode. Please check what you are trying to do before the end of the world comes. Also, you should construct additional pylons...";
    public static final String MUST_LOOK = "You must be looking at a sign to make this work... You Dun goofed!";
    public static final String IGN_ONLY = ChatColor.DARK_RED + "Sorry, you kinda need to get off this console and be connected. Your loss.";
    public static final String CONSOLE_ONLY = "This command may only be executed within console.";
    public static final String PLAYER_ONLY = "This command may only be executed within console, sorry.";
    public static final String NO_COMMAND_PERMS = "Command {0} does not have permissions set! Contact a developer, ASAP!";
    public static final String NO_COMMAND_SOURCE = "Command {0} does not have a command-source set! Contact a developer, ASAP!";
    public static final String ABDUCT_HAMMER = ChatColor.BLUE + "Unleash the power of..." + ChatColor.YELLOW + "Abducting Players!";
// Reporting and general badness
    public static final String YOU_BEEN_KICKED = "You have been kicked from the server with the following reason: ";
    public static final String REPORT = "Your report has been successfully filed. An administrator will be review your report shortly.";
    public static final String REPORT_REASON = " has reported the following player with the following reason: ";
    public static final String REPORTEE = "You have had a report filed against you.  A server administrator will review this soon. The reason is as follows: ";
    public static final String NO_MORE_THAN_TEN = ChatColor.DARK_RED + "You are unable to issue greater than 10 points. Please contact a host or higher if you feel it appropriate to issue this amount.";
    public static final String WARNING_BROADCAST_MESSAGE = "Issuing a warning to the following player with the warning reason: ";
    public static final String PLAYER_ALREADY_BANNED = "Not banning {0}: already banned!";
    public static final String BM_PLAYER_NOT_BANNED = "Not unbanning {0}: not banned!";
    public static final String OTHER_WARNING_AMOUNT = ChatColor.DARK_PURPLE + " has the following amount of warnings linked to their account. ";
    public static final String WARNINGS_AS_FOLLOWS = ChatColor.DARK_GREEN + "Your warning reasons are as follows with the amount of points issues in brackets.";
    public static final String WAIVE_WARNINGS_REASONS = ChatColor.DARK_GREEN + "Your waived warning reasons are as follows with the amount waived in brackets.";
    public static final String WARNING_REASONS = ChatColor.DARK_AQUA + "You have the following amount of warning points linked to your account: ";
// Host related
    public static final String CANNOT_REMOVE_POINTS = ChatColor.DARK_RED + "To remove warning points you will need to contact an Head Admin or above.";
    public static final String MYSQL_NOT_ENABLED = "MySQL has not been started. Please check your config to ensure you have enabled it";
    public static final String PLUGIN_ENABLED = "Version {0} by {1} is enabled";
    public static final String PLUGIN_DISABLED = "Version {0} is disabled";
    public static final String DISABLE_PLUGIN = "DarthCraftMod disabling as defined inside the config.";
    public static final String DEBUG_ENABLED = "Debug mode has been enabled - Please note that debug logs will be kept in English with no supported translations at the current time.";
    public static final String CANNOT_LOAD_BUILD_INFO = "Could not load build information!  Contact a developer immediately!";
    public static final String CANNOT_LOAD_BANS = "Could not load player ban: {0}!";
    public static final String BANS_MESSAGE = " {0} bans";
    public static final String LOADED = "Loaded";
    public static final String SAVED = "Saved";
    public static final String REMOVE_OLD_BANS = "Removing expired ban: {0}";
    public static final String BAN_GET_ERROR = "Sorry! " + ChatColor.AQUA + "There was a problem checking if you were banned.\n";
    public static final String APPEALAT_MESSAGE = "Please report this issue at";
    public static final String CANNOT_CHECK_BANS = "Problem checking ban status for {0}";
// Like-signs
    public static final String YOU_LIKED = "You have liked DarthCraft!  Thank you very much!";
    public static final String NOT_LIKED = "You have yet to like the server :( Please, type /like =D";
    public static final String ALREADY_LIKE_SIGN = "While I appreciate your mission to get as many likes as possible, this sign is already a like sign..";
    public static final String LIKESIGN_SET = "The like sign has been set!";
    public static final String LIKESIGN_REMOVED = "This like-sign has been removed! WHY YOU DO DIS?!?!?!?!?";
    public static final String NOT_LIKESIGN = "This is not actually a like-sign.. ";
//XP Related
    public static final String RANK_SET = "'s rank has been set to ";
    public static final String XP_LOW = "It appears that your XP is not high enough...  Better luck tomorrow!";
    public static final String NEW_XP = "Your new XP level is: ";
//MOTD Related
    /*
    
     Avalible Place Holders:
    
     # %serverversion% - Replaces with the current server version EG: 1.8.1
     # %playername% - Replaces with the players current IGN eg Wild1145
     # %randomplayer% - Replaces with a random online players anme
     # %onlinecount% - Replaces with the amount of online players
    
     */
    public static final String IGNMOTD_LINE1 = "&5Welcome to the DarthCraft server &6%playername% &7- &3Currently running MC %serverversion%";
    public static final String IGNMOTD_LINE2 = "&aPlease take the time to read the &c/Rules";
    public static final String IGNMOTD_LINE3 = "&2There are currently %onlinecount% players online having fun!";
    public static final String IGNMOTD_LINE4 = "&3If you need any assistance, please do ask either another player or an online member of staff.";
    public static final String IGNMOTD_LINE5 = "&3If you have any feedback, please do post on our forums at &awww.darthcraft.net !";
    //
    public static final String PING_LINE1 = "&5DarthCraft %serverversion%! &7| &3Welcome back &5&l%playername%";
    public static final String PING_LINE2 = "&1%randomplayer% &b is currently online, why dont you come join them?";
}
