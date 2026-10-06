## REMARK/NOTE
This is a redone version of the project. My first repository (It was called UniFLow) only had the test version of it committed and the new version was full of conflicts, and the project's Git files got messed up after moving the folder to another place. So at the last moment I decided to delete it, created a new project and repository, and recommitted everything here again, starting with the design sketches. Because of that, all commits are from the deadline day instead of being spread over the week I apologize for it((

# Matome

**Matome** (まとめ, "summary / bringing together") is a student organizer for university.
It puts your courses, tasks and deadlines on one screen, so you can see what you need to do
today and what's coming up this week.

This version (SIS 3) has the screens and styling. It uses hardcoded sample data in
`data/SampleData.kt`. Saving data on the device (Room) comes in the next phase.

Built with Kotlin, Jetpack Compose, Material 3 and Navigation Compose.

## Screens

| Screen | What it shows |
|---|---|
| **Home** (start) | "Welcome, Name" with a bell icon, an activity grid of tasks completed per day, a horizontal row of course cards (`LazyRow`), today's tasks with *Urgent* first, and this week's tasks |
| **All tasks** (list) | 12 tasks in a `LazyColumn` with filter chips (All / Urgent / Today / Done) and an empty state when a filter matches nothing |
| **Task details** (detail) | Gets `taskId` through navigation (`task/{taskId}`) and shows the course cover image, description, deadline, teacher, room and a *Mark as done* toggle |
| **Course details** | Gets `courseId` (`course/{courseId}`) and shows the course info and its tasks, or an empty state |
| **Settings** | Profile card and preference switches |

A bottom navigation bar switches between Home, All tasks and Settings. Every screen except Home has a back button.

Screenshots will be at the pre-release version

## Design: sketch vs app

Sketches of every screen are in [`/design`](design), with each part labeled by the Compose component that builds it (Scaffold, TopAppBar, LazyColumn, LazyRow, Column, Row, Box, Card, Image, Text).

**What changed from the Home sketch, and why**

- **Bottom bar.** The sketch has 4 icons (urgent, courses, schedule, settings) plus a round "+" button. The app has 3 items (Home, Tasks, Settings) and no "+" button yet, because the schedule, exams and "add task" screens are planned for a later phase. A button that opens nothing would be confusing.
- **Activity grid.** The sketch looks like GitHub's contribution graph with month labels and a hover tooltip. The app shows the last 12 weeks with day labels, a "Less → More" legend and a one-line summary of active days. Month labels and the tooltip are left out to keep it simple.
- **Course card cover.** In the sketch this was an "optional" box. In the app it is a course image from `res/drawable` on the course color.
- **Today's tasks.** The sketch has only the *Urgent* group. The app also has a *Later today* group for tasks due today that aren't urgent.

## How it's organized

```
app/src/main/java/com/example/matome/
├── MainActivity.kt          sets MatomeTheme + MatomeNavHost
├── navigation/MatomeNavHost.kt   routes + NavHost (ids passed as Int arguments)
├── data/                    Course, Task data classes + SampleData lists
└── ui/
    ├── theme/               Color.kt (the only file with color values), Theme.kt,
    │                        Type.kt, Spacing.kt (4/8/16/24 dp), CourseColors.kt
    ├── components/          TaskCard, CourseCard + CourseCover, SectionHeader, EmptyState,
    │                        InfoRow, ActivityHeatmap, MatomeTopBar, MatomeBottomBar
    └── screens/             Home, Tasks, TaskDetail, CourseDetail, Settings
```

**Styling rules:**

- Colors come only from `MaterialTheme.colorScheme`.
- Text styles come only from `MaterialTheme.typography`.
- Spacing comes only from `Spacing`.
- A course stores a color *name* (`CourseColor.Blue/Green/Yellow`), and `CourseColors.kt` maps it to `primary/secondary/tertiary`, so course colors switch correctly in dark mode.
- Changing `PrimaryLight` in `Color.kt` recolors the whole app.

## Run it

Open the project in Android Studio, let Gradle sync, and run the `app` configuration.
Every screen and component has a `@PreviewLightDark` preview, so you can see light and dark mode in the Preview panel without running the app.

##AI-usage
AI was used to format the readme file and to help with architecture of the project and theory explanation, everything else was made by me.


