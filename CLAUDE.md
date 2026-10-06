# CLAUDE.md

## Who you're working with

I'm a student, new to Android development. I'm building this app to learn —
to become genuinely confident writing Compose/Kotlin myself — not to ship a
finished product fast. Optimize every interaction for my learning, even when
that's slower than just writing the code for me.

## Project vision

**Matome** — a university student organizer. One app where students manage
their courses, class schedule, assignments and exams instead of juggling
several separate apps. Think of the idea behind Notion, but simpler,
phone-first, and built specifically for university/school students.

Repository: https://github.com/anxwavee/UniFLow

Core features:

- **Home dashboard** — the first screen. A quick overview of what matters
  now. Layout, top to bottom (based on my hand-drawn sketch):
  1. **Header** — "Welcome, {Name}" on the left, a notification bell icon
     on the right.
  2. **Statistics** — a GitHub-style activity heatmap: a grid of small
     squares (rows = days of the week, columns = weeks, month labels on
     top), where darker green means more tasks completed that day.
  3. **Folders / subjects** — a horizontally scrolling row of course cards.
     Each card uses its course's color and shows an optional cover/banner
     area, the course name, a progress bar with a percentage, and a small
     line of info (teacher, room, or time).
  4. **Today's tasks** — section title with a "See all" link. Tasks are
     grouped; the first group is **Urgent** (red label). Each task card has
     a colored bar on the left in its course's color, the course name, the
     task description, the deadline date, a "time left" countdown, and a
     red "!" marker for urgent tasks.
  5. **This week** — a further section of upcoming tasks, below today's.
  6. **Bottom navigation bar** — icons for tasks/urgent (!), courses,
     schedule/exams (clock) and settings (gear), plus a round floating
     "+" button to add something new.
- **Courses** — create, edit and delete courses. Each course has its own
  **folder** for organizing its notes, files, assignments, presentation
  topics and course information.
- **Tasks & assignments** — create, edit and delete them; each has a
  deadline and a priority; can be marked as completed.
- **Weekly schedule** — create and view a weekly class timetable. Each class
  has course, time, room and instructor.
- **Exams** — add and view upcoming exams. Each exam has subject, date &
  time, location and preparation tasks.
- **Exam prep** — before a test, the student can quickly review all previous
  tasks and topics for that course.
- **Settings** — profile, notifications, appearance, app preferences.
- **Local persistence** — the student's academic data is saved on the device.

Navigation (from the flow chart): Home Dashboard branches into Courses,
Exams, Schedule and Settings; each section drills into a list → a detail
screen → back to its list → back to the dashboard.

Design should be clean and easy to scan — the dashboard's whole job is to
let a student see "what do I need to do, and what's coming up" in seconds.
Color is functional: each course has its own color (e.g. blue, green,
yellow), and that color follows it everywhere — its folder card and the
left bar of its task cards — so tasks can be matched to a course at a
glance. Red is reserved for urgency.

## Development phases

Build in phases. Work through each one — don't skip ahead to later phases'
concerns.

1. **UI & Compose fundamentals (current phase).** Static screens with
   in-memory, hardcoded/sample data — no persistence yet. Home dashboard,
   course list, task list + task creation UI (deadline, priority, mark
   complete). Basic Navigation Compose between screens.
2. **Persistence & architecture basics.** Room database for courses, tasks,
   classes and exams. ViewModel + StateFlow, intro to the repository
   pattern. Full create/edit/delete flows.
3. **Remaining features & extras.** Weekly schedule, exams with prep tasks,
   course folders (notes, files — needs Android file/storage concepts),
   settings screens, deadline notifications (WorkManager).
4. **Networking & clean architecture.** Only if/when syncing between phone
   and laptop is actually wanted — proper domain/data/presentation
   layering, Hilt, Retrofit/backend.

Don't design phase 1 code in a way that assumes phase 4's architecture
prematurely. Keep it simple and appropriate to what I actually know how to
build right now — we refactor forward together when we get there.

**Current focus:** the Home Dashboard screen, matching the sketch described
under "Home dashboard" above: header, activity heatmap, horizontal course
cards, today's tasks (urgent first), this week, and the bottom navigation
bar with the "+" button. Still phase 1: hardcoded sample data (sample
courses, tasks and heatmap values), no Room yet. The nav bar and "+"
button can be visual-only at first; wiring them up comes later.

## How to work with me

- **Teach, don't just solve.** Explain the concept and reasoning first, then
  let me write the code myself.
- **Go step by step.** One small piece at a time — don't jump ahead until
  I've built and understood the current step.
- **When I ask "how do I do X," don't hand me the full answer immediately.**
  Ask what I think the approach might be first, or give a hint/nudge. Only
  give the full solution if I say I'm stuck or explicitly ask you to just
  show me.
- **When I write code, review it — don't rewrite it.** Explain what's wrong
  or what could be better, then let me try the fix myself first.
- **Focus explanations on what's actually new to me:** Kotlin syntax,
  Compose's declarative model, Android platform concepts (Activity/
  lifecycle, Context, Intents, Gradle), and Jetpack libraries (ViewModel,
  Navigation Compose, Room, Hilt, etc.).
- **Check my understanding periodically.** Ask me to explain something back
  in my own words before moving to the next piece.
- **Exception:** correct plain factual errors (wrong syntax, wrong
  terminology) directly and immediately. The "ask first" approach is for
  design and logic decisions, not basic facts.

Goal: by the end, I should be able to write Compose/Kotlin code confidently
without copy-pasting, and understand *why* it works — not just that it does.
