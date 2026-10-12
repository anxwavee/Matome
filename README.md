## REMARK/NOTE
This is a recommited version of the project. My first repository (It was called UniFLow) only had the test version of it committed and the new version was full of conflicts, and the project's Git files got messed up after moving the folder to another place. So at the last moment I decided to delete it, created a new project and repository, and recommitted everything here again, starting with the design sketches. Because of that, all SIS 3 commits are from the deadline day instead of being spread over the week I apologize for it((

# Matome

**Matome** (まとめ, "summary / bringing together") is a student organizer for university.
It puts your courses, tasks and deadlines on one screen, so you can see what you need to do
today and what's coming up this week.

Built with Kotlin, Jetpack Compose, Material 3, Navigation Compose, ViewModel and StateFlow.

## Versions

| Version | What it adds |
|---|---|
| **SIS 3** | All screens and styling, navigation between them, hardcoded sample data in `data/SampleData.kt` |
| **SIS 4** | MVVM refactor of the **All tasks** screen: `TaskRepository` → `TasksViewModel` → stateless `TasksScreen`, with search, filters, mark-as-done, loading / empty / error states, and state that survives rotation |

Saving data on the device (Room) comes in the next phase. Thanks to MVVM, only the repository will need to change.

## Screens

| Screen | What it shows |
|---|---|
| **Home** (start) | "Welcome, Name" with a bell icon, an activity grid of tasks completed per day, a horizontal row of course cards (`LazyRow`), today's tasks with *Urgent* first, and this week's tasks |
| **All tasks** (list, MVVM) | A search field, filter chips (All / Urgent / Today / Done) and a checkbox on every task to mark it done. Shows a loading spinner while the repository "loads", an error message with a *Retry* button, and an empty state when nothing matches. Search text, filter and checkboxes survive rotation |
| **Task details** (detail) | Gets `taskId` through navigation (`task/{taskId}`) and shows the course cover image, description, deadline, teacher, room and a *Mark as done* toggle |
| **Course details** | Gets `courseId` (`course/{courseId}`) and shows the course info and its tasks, or an empty state |
| **Settings** | Profile card and preference switches |

A bottom navigation bar switches between Home, All tasks and Settings. Every screen except Home has a back button.

Screenshots will be at the pre-release version

## Design: sketch vs app

Sketches of every screen are in [`/design`](design), with each part labeled by the Compose component that builds it (Scaffold, TopAppBar, LazyColumn, LazyRow, Column, Row, Box, Card, Image, Text).
The SIS 4 architecture plan is in [`design/architecture.md`](design/architecture.md).

**What changed from the Home sketch, and why**

- **Bottom bar.** The sketch has 4 icons (urgent, courses, schedule, settings) plus a round "+" button. The app has 3 items (Home, Tasks, Settings) and no "+" button yet, because the schedule, exams and "add task" screens are planned for a later phase. A button that opens nothing would be confusing.
- **Activity grid.** The sketch looks like GitHub's contribution graph with month labels and a hover tooltip. The app shows the last 12 weeks with day labels, a "Less → More" legend and a one-line summary of active days. Month labels and the tooltip are left out to keep it simple.
- **Course card cover.** In the sketch this was an "optional" box. In the app it is a course image from `res/drawable` on the course color.
- **Today's tasks.** The sketch has only the *Urgent* group. The app also has a *Later today* group for tasks due today that aren't urgent.

## Architecture (MVVM, SIS 4)

Think of a restaurant: the **View** is the waiter (shows things, passes orders), the **ViewModel** is the chef
(takes orders, prepares the state, survives rotation), and the **Repository** is the fridge (stores the data).
Events go up, state comes down.

```
View (waiter)                 ViewModel (chef)                 Repository (fridge)
TasksScreen  ──── events ───▶ TasksViewModel  ──── asks ────▶ TaskRepository
TasksScreen  ◀─── uiState ─── (TasksUiState)  ◀─── data ───── (tasks, delay(), shouldFail)

TasksRoute connects TasksViewModel and TasksScreen with collectAsStateWithLifecycle()
```

| Layer | File | Job |
|---|---|---|
| Data | `data/TaskRepository.kt` | Owns the task list. `getTasks()` waits with `delay(1000)` like a server and throws an error when `shouldFail = true`. `toggleCompleted(taskId)` marks a task done / not done |
| State | `ui/screens/TasksUiState.kt` | `data class TasksUiState(tasks, courses, searchQuery, selectedFilter, isLoading, error)` — only `val` properties. Also holds the `TaskFilter` enum |
| ViewModel | `ui/screens/TasksViewModel.kt` | Private `_uiState: MutableStateFlow`, public read-only `uiState: StateFlow`. Updates state only with `copy()`. All filtering and searching happens here in `applyFilters()` |
| Glue | `TasksRoute` in `ui/screens/TasksScreen.kt` | Gets the ViewModel with `viewModel()`, collects `uiState` with `collectAsStateWithLifecycle()` and passes it plus the event lambdas to the screen |
| View | `TasksScreen` in `ui/screens/TasksScreen.kt` | Stateless: receives `uiState` and lambdas, draws them, and does not know the ViewModel exists |

**Four UI situations**

| State | When |
|---|---|
| Loading | `isLoading = true` → spinner |
| Error | `error != null` → message + *Retry* button |
| Empty | not loading, no error, `tasks` is empty → empty state |
| Content | `tasks` is not empty → task list |

`TasksScreen.kt` has a `@Preview` for each of the four states, built from fake `TasksUiState` objects, so every state can be seen without running the app.

**Events (user actions handled in the ViewModel)**

- `onSearchChange(query)` — typing in the search field
- `onFilterSelected(filter)` — tapping a filter chip
- `onToggleCompleted(taskId)` — tapping the checkbox on a task
- `onRetry()` — the *Retry* button on the error state

To test the error state, set `shouldFail = true` in `TaskRepository`.

### Trace one click

When the user taps the **"Urgent"** chip, the `FilterChip` in `TasksScreen` calls `onFilterSelected(TaskFilter.Urgent)`.
`TasksScreen` does not change anything itself; the lambda was passed in by `TasksRoute`, and it calls
`TasksViewModel.onFilterSelected()`. The ViewModel updates the private `_uiState` with
`it.copy(selectedFilter = TaskFilter.Urgent, tasks = applyFilters(...))`, so only urgent, not-completed tasks are kept.
`TasksRoute` collects `uiState` with `collectAsStateWithLifecycle()`, so Compose gets the new state and recomposes
`TasksScreen`: the "Urgent" chip is selected and the list shows only urgent tasks (or the empty state if there are none).
Because the filter lives in the ViewModel, it is still selected after rotating the phone.

## Rotation proof (SIS 4)

`MainActivity` logs every lifecycle method, and `TasksViewModel` logs `init` and `onCleared`.
I opened **All tasks**, rotated the phone twice, then pressed Back. Logcat (filter `tag:MainActivity | tag:TasksViewModel`):

```
TasksViewModel  D  init
MainActivity    D  onPause
MainActivity    D  onStop
MainActivity    D  onDestroy
MainActivity    D  onCreate
MainActivity    D  onStart
MainActivity    D  onResume
MainActivity    D  onPause
MainActivity    D  onStop
MainActivity    D  onDestroy
MainActivity    D  onCreate
MainActivity    D  onStart
MainActivity    D  onResume
TasksViewModel  D  onCleared
```

**Why the Activity was recreated but the ViewModel was not**

Rotating the phone is a configuration change, so Android destroys `MainActivity` and creates a new one
to draw the screen in the new orientation. That is why we see `onDestroy` and then `onCreate` twice.
`TasksViewModel` is not stored inside the Activity but in a `ViewModelStore` that outlives this
recreation, so the new Activity gets the same ViewModel back and `init` is not called again. Because the
search text, the selected filter and the completed tasks live in the ViewModel's `uiState`, they are
still there after rotation. `onCleared()` only runs when I really leave the screen with Back, not on rotation.

## How it's organized

```
app/src/main/java/com/example/matome/
├── MainActivity.kt          sets MatomeTheme + MatomeNavHost, logs lifecycle methods
├── navigation/MatomeNavHost.kt   routes + NavHost (ids passed as Int arguments), All tasks → TasksRoute
├── data/                    Course, Task data classes, SampleData lists,
│                            TaskRepository (owns task data, delay(), shouldFail)
└── ui/
    ├── theme/               Color.kt (the only file with color values), Theme.kt,
    │                        Type.kt, Spacing.kt (4/8/16/24 dp), CourseColors.kt
    ├── components/          TaskCard, CourseCard + CourseCover, SectionHeader, EmptyState,
    │                        InfoRow, ActivityHeatmap, MatomeTopBar, MatomeBottomBar
    └── screens/             Home, Tasks (TasksRoute + TasksScreen), TasksUiState,
                             TasksViewModel, TaskDetail, CourseDetail, Settings
```

**Styling rules:**

- Colors come only from `MaterialTheme.colorScheme`.
- Text styles come only from `MaterialTheme.typography`.
- Spacing comes only from `Spacing`.
- A course stores a color *name* (`CourseColor.Blue/Green/Yellow`), and `CourseColors.kt` maps it to `primary/secondary/tertiary`, so course colors switch correctly in dark mode.
- Changing `PrimaryLight` in `Color.kt` recolors the whole app.

## Run it

Open the project in Android Studio, let Gradle sync, and run the `app` configuration.
Screens and components have previews, so you can check them in the Preview panel without running the app
(light and dark with `@PreviewLightDark`, and loading / content / empty / error for All tasks).