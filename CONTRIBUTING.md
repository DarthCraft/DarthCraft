# Contributing to the DarthCraft Plugin #

The DarthCraft Plugin is a simple Bukkit Plugin designed to improve the experiance of the players and staff on the DarthCraft server.

For those who wish to contribute, we encourage you to fork the repository and submit pull requests. Below you will find guidelines that will explain this process in further detail.

## Quick Guide ##
1. Create or find an issue on our [Issue Tracker](https://github.com/DarthCraft/DarthCraft/issues) however please check for the release tags. Some issues have already been assigned to a developer or has a time on when we are releasing the feature into the plugin.
2. Does your change work for a 100% Survival Server?
3. Fork the DarthCraft Plugin if you haven't done so already.
4. Make a branch dedicated to your change.
5. Make your change.
6. Commit your change according to the [committing guidelines](#committing-your-changes).
7. Push your branch and submit a pull request.

## Getting Started ##
* Search the issue tracker for your bug report or feature request.
* If the issue does not exist already, create it.
  * Clearly describe the issue.
  * If your issue is a bug, describe the steps needed to reproduce it.
  * If your issue is a feature request, ensure it fits the server style (Pure Survival) and describe your feature in detail.
* Fork the repository on GitHub.

## Does the change fit the server style? ##

In short, does it make the game more fun for the players and will it be OP? Is it something that has been suggested on the forums and is it something you think players want? If "No" is the response to any of them, then the feature isnt for this plugin. Simple as that.

## Making Changes ##
* Create a topic branch from where you want to base your work.
  * This is usually the master branch.
  * To quickly create a topic branch based on master, use `git checkout master` followed by `git checkout -b <name>`. Avoid working directly on the `master` branch.
* Make sure your change meets our [code requirements](#code-requirements).

### Code requirements ###
* Code must be written in [Allman style](http://en.wikipedia.org/wiki/Indent_style#Allman_style), and that it follows [Java Code Conventions](http://www.oracle.com/technetwork/java/codeconventions-150003.pdf).__
* No tabs; use 4 spaces for indentation.
* No trailing whitespaces for code lines, comments or configuration files.
* No CRLF line endings, only LF is allowed.
  * For Windows-based machines, you can configure Git to do this for your by running `git config --global core.autocrlf true`.
  * If you're running a Linux or Mac OSX, you should run `git config --global core.autocrlf input` instead.
  * For more information about line feeds. See this [this article](http://adaptivepatchwork.com/2012/03/01/mind-the-end-of-your-line/).
* No 80 character line limit or 'weird' midstatement newlines.
* Additions should be compiled, complete and tested before committing.
* Avoid using `org.bukkit.Server.dispatchCommand()`. Commits that make use of it will likely be rejected.
* Files must always end with a newline.
* Avoid nested code structures.

## Committing your changes ##
* Ensure that is is formatted correctly. 
* Describe your changes in the commit description.
* For a prolonged description, continue on a new line.
* The first description line should be once sentence and should not exceed 10 words.
* The first description line should contain either:
  * For a bug-related issue: "Resolves _#issue_".
  * For a feature request: "Fixes _#issue_".
  * "#issue" is the issue number number you based your work on.

#### Example commit message ####
```
Added in BarAPI Announcements. Resolves #8
I have now added in the announcements system for BarAPI meaning that the server will now announce both in chat and on the bar. 
```

## Submitting Your Changes ##
* Push your changes to the topic branch in your fork of the repository.
* Submit a pull request to this repository.
  * Explain in detail what each one of your commits changes and point out any big changes.
* Wait as a developer evaluates your changes.
* Do not add any prefixes or tags.

## Tips - How To Get Your Pull Request Accepted ##
* Please make sure your changes are written such as other features would be. For example: Commands have their own class and extend TFM_Command.
* Do not increment the version number.
* If you want to add multiple changes, please make one pull request per change. This way, it's easier to accept your changes faster and won't block the other changes if there is an issue with a specific line of code.
* Please refrain from using an excessive amount of commits. As few as possible is generally the best.
* Please do not spread your contribution over several pull-requests however dont mix additions. If your adding feature A and B, send separate pull requests for them.

## Additional Resources ##
* [Bug tracker](https://github.com/DarthCraft/DarthCraft/issues)
* [General GitHub documentation](http://help.github.com/)
* [GitHub pull request documentation](http://help.github.com/send-pull-requests/)