# Nanit Anniversary home assignment
The app gives user a possibility to set child's name, birthday and image, generate bithday anniversary screen and share it to other apps.
## Architecture
### Clean Architecture
App is developed with Clean Architecture principles. So, there are 3 layers of app architecture:
- Data – contains logic for working with storing and retrieving data from local storage. Contains storages and repository implementations.
- Domain – contains application business rules, like handling retrieved data and calculating child's age. Contains repository contracts and use cases.
- Presentation – responsible for handling user actions, displaying retrieved view data and working with UI layer.

This approach gives possibility to make changes in specific layer without affecting others, add and modify new features.

### MVVM
UI layer is implemented with MVVM pattern, that helps to separate logic for handling UI data and displaying it.

## Implementation steps
Project implementation is performed due to sent specifications. It consists of five steps. It was also added the 0 step with project setup.

### Step 0. Project setup
5 modules were set up for developing project :
- `app` - module with Application class for DI configuration.
- `androidCore` - module for reusable utils.
- `data` - module for working with platform dependent data APIs (for shared references).
- `domain` - module for business logic (age calculation, working with data layer).
- `presentation` - module for UI (Fragments, Activities, ViewModels).

Also some libraries were added, like [Koin](https://insert-koin.io/docs/reference/introduction) for DI, [Navigation component](https://developer.android.com/guide/navigation/navigation-getting-started) and othrs.

Tag: [#0.project_setup](https://github.com/sigma-stochanskyi/NanitTask/releases/tag/0.project_setup)

### Step 1. Profile Screen
The profile screen is pretty simple and consists of:
- application logo;
- profile image, clicking on image opens image picking source (files or camera);
- name input field;
- birthday noneditable input field, clicking this fields opens calendar dialog to pick the child's birthday.

Image picking is implemented using `TakeOrPickImageContract` that is developed using Activity Result API with creating chooser of `MediaStore.ACTION_IMAGE_CAPTURE` and `Intent.ACTION_GET_CONTENT` with checking if device is able to handle these intents.

For birthday picking app uses `MaterialDatePicker.Builder.datePicker()` with setting date constraint from 4 years ago until today.

[Coil](https://coil-kt.github.io/coil/http://) is used for image loading. This is lightweight image loader with Kotlin-friendly API that allows flexible loading setting like fallback and error images, placeholders, cache policy, binding request to lifecycle and others.

Tag: [#1.profile_screen](https://github.com/sigma-stochanskyi/NanitTask/releases/tag/1.profile_screen)

### Step 2. Profile data saving
Saving profile data is implemented using `SharedPreferences` API. Profile data is updated in `ProfileViewModel` after user performs actions on profile screen. Data on screen is updated right after the screen is loaded, namely, when `Fragment.onViewCreated()` is executed.
Data is saved after user clicks "Show birthday button", that is available only after entering at least name and birthday

On data layer `ProfileDataStorage` is responsible for working with `SharedPreferences`. For saving and retrieving data on domain layer `GetProfileUseCase` and `SetProfileUseCase` are used. Then, `ProfileViewModel` saves and retrieves data using these use cases.

Tag: [#2.profile_saving](https://github.com/sigma-stochanskyi/NanitTask/releases/tag/2.profile_saving)

### Step 3. Birthday screen
Birthday screen opening is allowed only when name and birthday are not empty. So, birthday screen is opened right after user clicks "Show birthday screen".

The screen can be represented in one of three visual appearances. To solve this task interface `BirthdayViewAppearance` was implemented and contains configurations like view components colors, image resources and others. There are three implementations of this interface and they define these parameters for three different appearances.

The screen is implemented using `ConstraintLayout`. This layout gives an API to develop flexible layout without sizes hardcoding.

So, the screen consists of:
- Title (name, age value and unit) - is implemented using packed constraint chain.
- Profile image (`ShapeableImageView`) - constrained horizontally to parent with margin, vertically to title and logo.
- Camera button - constrained to the profile image and translated to the angle at runtime using sine and cosine of the angle. As profile image doesn't have constant size, camera button cannot be implemented by setting `layout_constraintCircleRadius`, and setting it at runtime requires changing layout params and therefore measuring layout again.
- Logo - implemented using `ImageView`.
- Share button - implemented using `MaterialButton` with bottom margin.

For displaying age value `AgeView` is implemented. A custom view that receives age value, splits it into digits, finds digit drawables and draws them on canvas.

Custom insets handling logic was implemented to achieving screen to draw behind status bar and button below status bar.
So, `InsetAwareNavigationContainerFragment` is a container for navigation graph and dispatches intents to each child, and if intents are not consumed, the they are applied to child as margins.
Function `View.onApplyDispatchInsetsWithMargins()` applies insets to view as margins and consumes them. 

Tag: [#3.birthday_screen](https://github.com/sigma-stochanskyi/NanitTask/releases/tag/3.birthday_screen)

### Step 4. Birthday screen image picker
Image picker is opened when user clicks a camera icon. The picker is reused from Profile screen. After user picks a profile image, it is updated in both screens. To update it in shared preferences `SetProfileImageUseCase` is used.

Tag: [#4.birthday_image_picker](https://github.com/sigma-stochanskyi/NanitTask/releases/tag/4.birthday_image_picker)

### Step 5. Share birthday
User is able to share his child's anniversary by clicking "Share" button.

Share, back and camera button visibility is changed to invisible when sharing the screen. To create the screen snapshot layout is drawn on a bitmap. Afterwards the bitmap is written into previously created file.

For sending created file to other apps intent with action `Intent.ACTION_SEND` is used. It allows other apps to catch this intent with file, which is passed via the intent extras with `Intent.EXTRA_STREAM` key.

Tag: [#5.birthday_share](https://github.com/sigma-stochanskyi/NanitTask/releases/tag/5.birthday_share)
