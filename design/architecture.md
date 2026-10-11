# Matome — MVVM Architecture (SIS4)

## Restaurant picture
View (waiter)          ViewModel (chef)           Repository (fridge)
TasksScreen    ──events──▶  TasksViewModel  ──asks──▶  TaskRepository
TasksScreen    ◀──state──   (TasksUiState)  ◀──data──  (tasks + courses, delay(), shouldFail)

TasksRoute = connects TasksViewModel to TasksScreen (collectAsStateWithLifecycle)

## Screens (View)
- HomeScreen - dashboard (heatmap, courses, today's tasks, this week)
- TasksScreen — all tasks with search + filter chips  ← refactored to MVVM
- TaskDetailScreen, CourseDetailScreen, SettingsScreen

## ViewModels
- TasksViewModel → serves TasksScreen

## Order board: TasksUiState
data class TasksUiState(
val tasks: List<Task> = emptyList(),      // already filtered + searched
val courses: List<Course> = emptyList(),
val searchQuery: String = "",
val selectedFilter: TaskFilter = TaskFilter.All,
val isLoading: Boolean = false,
val error: String? = null,
)
Four situations:
- loading → isLoading = true
- error   → error != null
- empty   → not loading, no error, tasks is empty
- content → tasks is not empty

## Events (user actions)
- onSearchChange(query: String)        — typing in the search field
- onFilterSelected(filter: TaskFilter) — tapping a chip
- onToggleCompleted(taskId: Int)       — tapping the checkbox on a task
- onRetry()                            — the "Retry" button on the error screen

## Repository
- TaskRepository — owns the tasks and courses lists (moved from SampleData).
  getTasks() waits with delay(1000) like a server.
  shouldFail = true makes it throw an error so we can see the error state.

## Trace one click
When the user taps the "Urgent" filter chip, TasksScreen does not change anything
by itself. It just calls the lambda it received, onFilterSelected(TaskFilter.Urgent).
TasksRoute passed that lambda in, and it calls viewModel.onFilterSelected() in
TasksViewModel. The ViewModel updates the private _uiState with
copy(selectedFilter = TaskFilter.Urgent, tasks = <only urgent, not completed tasks>),
so the filtering happens in the ViewModel, not in the composable. Because TasksRoute
collects uiState with collectAsStateWithLifecycle(), Compose sees the new state and
recomposes TasksScreen: the "Urgent" chip is now selected and the list shows only
urgent tasks (or the empty state if there are none). Because the filter is stored
in the ViewModel, it is still selected after rotating the phone