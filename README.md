# THIS IS NOT FINISHED PROJECT

The idea is that some phones have front facing camera, but lack the ambient light sensor (yep, I am looking at you Samsung Galaxy Ace 2). Solution?
Wait for "screen on" event, take a photo, measure the luminance, and apply system wide brightness accordingly.

The weak point? Oh well, Google API does not have a concept of applying system-wide settings (just setting them).

At that point, this almost finished project, simply died. Because for any method (or rather trick) that could somehow fool Android
and apply brightness, I counter-scenario (not even corner case) for which this or that method will fail.

## Further development

I left some "under development" comment, for easier continuation, but I doubt it will happen. If (!) I find some new trick of applying
brightness (system-wide), sure, but until then -- this code is dead.

Use it, if you like, or let me know if you found out some new way to APPLY system settings in Android.

I put this code on GitHub for single purpose -- for anyone who could come up with identical idea (it is not uncommon; I found such solutions
as "buy a phone **with** ambient light sensor"), it might save some sweat of building testbed. So, here you have it -- experiment :-).

## LICENSE

Be fair and use common-sense, which translates, if you would like to grab entire code or use it in your own app, do it, as long you put 
appropriate credits (containing my full name) in a place available for non-dev user (for Google Play Store it should be Play Store app web page).

That's all, have fun!
 
*Maciej "MACiAS" Pilichowski*
                    