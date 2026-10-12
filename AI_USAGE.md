# AI Usage – SIS 4 (MVVM refactor)

## 1. Which AI tools did I use?
- **Claude** (claude.ai)

## 2. How did I use AI?
The AI wrote the code for this refactor. My job was to understand every part
of it, check that it follows MVVM correctly, test it, and be able to explain
and change it myself without AI.

My process:
1. First I asked for the **theory** before any code: what MVVM is, what
   unidirectional data flow means, why a ViewModel survives rotation, and what
   `StateFlow` is.
2. Then I asked for a **step-by-step plan** in the right order: design →
   repository → UiState → ViewModel → stateless screen → Route → previews →
   logs → README.
3. The AI generated the code step by step. After each step I read it, asked
   about the parts I didn't understand, ran the app, and committed.

## 3. Which parts were AI-generated?
- `data/TaskRepository.kt` and `data/AppContainer.kt`
- `ui/screens/TasksUiState.kt` (UiState + `TaskFilter`)
- `ui/screens/TasksViewModel.kt`
- `ui/screens/TasksScreen.kt` (`TasksRoute`, stateless `TasksScreen`, the 4 previews)
- `HomeViewModel` / `HomeUiState` / `HomeRoute`, and the detail screen ViewModel
- The lifecycle logs in `MainActivity`

What I did myself: read and understood all of it, tested it on the
emulator/phone (rotation test, error test, previews), took the Logcat output,
made the `/design` diagram, and wrote the explanations in the README in my own
words.

## 4. What did I have to check or fix?
While reviewing the code, I found these problems and fixed them with the AI:
- **Each ViewModel had its own repository.** Every ViewModel created
  `TaskRepository()` by itself, so marking a task done on one screen didn't
  show on another. We moved to one shared instance in `AppContainer`.
- **State was lost on rotation in the task detail screen.** The "done" flag
  was kept in `remember { mutableStateOf(...) }`, which disappears when the
  Activity is recreated. It's real data, so it moved into a ViewModel.
- **The error state could not be shown.** `shouldFail` existed but nothing
  ever set it to `true`, so I added a way to trigger it for testing.

## 5. One place where logic was in the wrong layer, and how it was moved
In my SIS3 code (also written with AI), `HomeScreen` filtered the tasks itself.
Inside the composable it split the list into `openTasks`, `todayUrgent`,
`todayOther` and `thisWeek`. `CourseDetailScreen` also filtered tasks by
`courseId` inside the composable. This is business logic in the View layer.
In the restaurant picture, the waiter was cooking.

How it was moved:
1. `HomeUiState` now holds the ready-made lists (`todayUrgent`, `todayLater`,
   `thisWeek`) plus `courses`, `activity` and `isLoading`.
2. `HomeViewModel` gets the data from `TaskRepository` and does the splitting
   in one private function.
3. `HomeRoute` collects the state with `collectAsStateWithLifecycle()` and
   passes it to `HomeScreen`.
4. `HomeScreen` now only receives `uiState` and lambdas and just draws. It has
   no `.filter` calls, and I can preview it with a fake `HomeUiState`.

## 6. What I understood and can explain without AI
- **Rotation:** the Activity is destroyed and recreated, but the ViewModel is
  stored in a `ViewModelStore` that Android keeps across configuration changes,
  so the same ViewModel (with the same state) is reused. In Logcat the
  Activity logs `onDestroy` → `onCreate`, but the ViewModel does not log
  `init` again. `onCleared()` only runs when the screen leaves the back stack.
- **Why `_uiState` is private:** only the ViewModel can change the state; the
  screen can only read it and send events. This keeps data flowing one way.
- **Why I can preview the error state:** the screen only gets `uiState` and
  lambdas, so I can give it a fake `TasksUiState(error = "...")`.
- **The path of one click:** `Checkbox` in `TasksScreen` →
  `onToggleCompleted` lambda → `TasksRoute` →
  `TasksViewModel.onToggleCompleted()` → `TaskRepository.toggleCompleted()` →
  `_uiState.update { it.copy(...) }` → `collectAsStateWithLifecycle` →
  the screen recomposes and the task is crossed out.

## 7. How I checked that it works
- Rotation test: typed a search, picked a filter, ticked a task, rotated —
  everything stayed.
- Error test: turned on `shouldFail`, saw the error message, pressed Retry,
  and the list loaded.
- Checked all four previews (loading, content, empty, error) in Android Studio.
- Took the Logcat output after rotation and added it to the README.
