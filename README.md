video demo: https://drive.google.com/file/d/1K5pp6upukN46HNqWGREccAoDrRRLF82V/view?usp=drive_link

apk link: https://drive.google.com/file/d/11lFcixc4TpQiTdsahE5MnLAyLvBReodI/view?usp=drive_link

Points to Consider:
1. Used Kotlin for the Application
2. MVVM architecture is followed when it is required
3. API Integration is done using retrofit and gson converter is used to process the response
4. Navigation Map is not used in the project, because my current understanding of the concept is not sufficient for the use-case(due to time-constraint i couldn't try different things),
   instead used the best practice i know of.
5. used basefragment for linksfragment which utilizes a viewmodel and others use a basefragmentwithoutviewmodel where viewmodel is not used in those fragment
6. baseviewmodel  is also used, although it's  potential is not being used
7. tried to replicate the figma ui as much as possible
8. clean code and architecture alias "best practices" are followed to the best of my knowledge


Libraries used:

i. retrofit -> for apis

ii. MPAndroidChart -> for graphs

iii. coroutines -> for background tasks

iv. glide -> for loading images in the UI

Followed the DRY principle as much as could but a few layout files are made in a rush so, there are very few cases where
it is not followed.

Any Feedback or points to improve are completly appreciated. Do let me know so I can make thy art better.
Thanx in advance.
