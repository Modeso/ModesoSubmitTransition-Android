# ModesoSubmitTransition-Android

<p align="center">
  <img src="https://media.licdn.com/mpr/mpr/shrink_200_200/AAEAAQAAAAAAAAZsAAAAJDM2NTU0MDA1LTA3YmEtNGUyMC05YmZjLTIxMDNlZWZlM2ZkMQ.png">
</p>

ModesoSubmitTransition-Android is an android library written in JAVA. 
It's insperated by [Yaroslav Zubko](https://dribbble.com/Yar_Z)'s [design](https://dribbble.com/shots/1945593-Login-Home-Screen).
It enable transite button to loading indecator while doing some actions then start transite to next screen.

<img src="https://github.com/Modeso/ModesoSubmitTransition-Android/blob/master/ModesoSubmitTransition.gif">

- [Requirements](#requirements)
- [Installation](#installation)
- [Usage](#usage)
- [Options](#options)
- [Communication](#communication)
- [Credits](#credits)
- [License](#license)

## Requirements

- minSdkVersion 15

## Installation


## Usage

- in **XML**
```
  <ch.modeso.progressbuttonlibrary.ProgressButton
            android:id="@+id/loginButton"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_gravity="center_horizontal"
            android:layout_marginBottom="20dp"
            android:layout_marginLeft="40dp"
            android:layout_marginRight="40dp"
            android:contentDescription=""
            android:onClick="onClickLoginButton"
            android:paddingBottom="15dp"
            android:paddingTop="15dp"
            android:textAllCaps="false"
            android:textColor="@color/pb_white"
            android:textSize="18sp"

            app:pb_colorIndicator="@color/pb_grey"
            app:pb_cornerRadius="48dp"
            app:pb_selectorComplete="@color/colorAccent"
            app:pb_selectorError="@color/colorAccent"
            app:pb_selectorIdle="@color/colorAccent"
            app:pb_textIdle="@string/signin" />

```
- in **Activity** or **Fragment**
```
   - showProgress(): use it to start button transition to loading indicator.
   - public <T> void onActionComplete(final Class<T> className, final boolean noHistory): use it to strat loading
   indicator animation to fill the screen and start transition to the next screen, if the second parameter set to
   true then the next screen will opened with no stack history.
   - showError(): use it to stop loading indicator animation and transite it to button again with error icon.
   - isIdle(): use it to check if the button in the ideal stat.
   - isErrorOrCompleteOrCancelled(): to check if the button in error or complete stat.
```

## Options
- XML **Attributes**
  - **pb_colorIndicator**: loading indicator color.
  - **pb_cornerRadius**: button corner radius.
  - **pb_selectorIdle**: button background color in idle case.
  - **pb_selectorComplete**: button background color in case that action complete successfully.
  - **pb_selectorError**: button background color in case that action complete with errors.
  - **pb_iconError**: button icon in case that action complete with errors.
  - **pb_textIdle**: button text.
  

## Communication

- If you **found a bug**, open an issue.
- If you **have a feature request**, open an issue.
- If you **want to contribute**, submit a pull request.

## Credits

ModesoSubmitTransition-Android is owned and maintained by [Modeso](http://modeso.ch). You can follow them on Twitter at [@modeso_ch](https://twitter.com/modeso_ch) for project updates and releases.

## License

ModesoSubmitTransition-Android is released under the MIT license. See LICENSE for details.
