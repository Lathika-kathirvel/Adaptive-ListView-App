# Implementation Plan - Adaptive ListView App with Detail ImageView

The goal is to create an adaptive Android application that displays a list of items using a `ListView`. When an item is selected, its details (specifically an `ImageView`) are shown. The app will be "adaptive," meaning it will show a single pane on small screens (phone) and two panes (list + detail) on larger screens (tablets/foldables).

## User Review Required

> [!IMPORTANT]
> I will be using `SlidingPaneLayout` to achieve adaptivity. This is the recommended approach for list-detail patterns in traditional Android Views. It automatically handles showing one or two panes based on screen width.

## Proposed Changes

### 1. Dependencies
- Add `androidx.slidingpanelayout:slidingpanelayout` to `libs.versions.toml` and `build.gradle.kts`.

### 2. Data Model
- Create a `Fruit` data class to hold item name and an image resource.

### 2. Layouts

#### [NEW] [list_item.xml](file:///C:/Users/USER/AndroidStudioProjects/AdaptiveListViewApp/app/src/main/res/layout/list_item.xml)
- Define the layout for each row in the `ListView`.
- Contains an `ImageView` and a `TextView`.

#### [MODIFY] [activity_main.xml](file:///C:/Users/USER/AndroidStudioProjects/AdaptiveListViewApp/app/src/main/res/layout/activity_main.xml)
- Replace the current layout with a `androidx.slidingpanelayout.widget.SlidingPaneLayout`.
- **Pane 1**: `ListView` to display the list of fruits.
- **Pane 2**: A `LinearLayout` containing an `ImageView` and a `TextView` for the details.

### 3. Application Logic

#### [MODIFY] [MainActivity.kt](file:///C:/Users/USER/AndroidStudioProjects/AdaptiveListViewApp/app/src/main/java/com/example/adaptivelistviewapp/MainActivity.kt)
- Create a list of sample `Fruit` data.
- Implement a custom `ArrayAdapter` for the `ListView`.
- Set up the `ListView` item click listener to update the detail pane and open it (if in single-pane mode).
- Correct the `enableEdgeToEdge` implementation by adding the missing `android:id="@+id/main"` to the root layout.

## Verification Plan

### Automated Tests
- I will check if the project builds successfully using `gradle_build`.

### Manual Verification
- The user can verify the adaptivity by:
    1. Running on a phone emulator: Clicking an item should slide in the detail view.
    2. Running on a tablet emulator: The list and detail should be visible side-by-side.
    3. Resizing a foldable emulator: The layout should transition between one and two panes.
