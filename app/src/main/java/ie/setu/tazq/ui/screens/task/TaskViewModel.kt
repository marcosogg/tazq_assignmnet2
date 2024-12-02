package ie.setu.tazq.ui.screens.task

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ie.setu.tazq.data.Task
import ie.setu.tazq.data.repository.TaskRepository
import ie.setu.tazq.ui.components.task.TaskPriority
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val repository: TaskRepository
) : ViewModel() {

    private val _taskTitle = MutableStateFlow("")
    val taskTitle: StateFlow<String> = _taskTitle.asStateFlow()

    private val _taskDescription = MutableStateFlow("")
    val taskDescription: StateFlow<String> = _taskDescription.asStateFlow()

    private val _taskPriority = MutableStateFlow(TaskPriority.MEDIUM)
    val taskPriority: StateFlow<TaskPriority> = _taskPriority.asStateFlow()

    private val _selectedCategory = MutableStateFlow("Personal")
    val selectedCategory: StateFlow<String> = _selectedCategory.asStateFlow()

    private val _isTitleValid = MutableStateFlow(false)
    val isTitleValid: StateFlow<Boolean> = _isTitleValid.asStateFlow()

    private val _isDescriptionValid = MutableStateFlow(false)
    val isDescriptionValid: StateFlow<Boolean> = _isDescriptionValid.asStateFlow()

    fun updateTaskTitle(title: String) {
        _taskTitle.value = title
        validateTitle(title)
    }

    fun updateTaskDescription(description: String) {
        _taskDescription.value = description
        validateDescription(description)
    }

    fun updateTaskPriority(priority: TaskPriority) {
        _taskPriority.value = priority
    }

    fun updateSelectedCategory(category: String) {
        _selectedCategory.value = category
    }

    private fun validateTitle(title: String) {
        _isTitleValid.value = title.isNotEmpty() && title.length >= 3
    }

    private fun validateDescription(description: String) {
        _isDescriptionValid.value = description.length <= 500
    }

    fun isFormValid(): Boolean {
        return _isTitleValid.value && _isDescriptionValid.value
    }

    fun addTask() {
        if (!isFormValid()) return

        val newTask = Task(
            title = _taskTitle.value,
            priority = _taskPriority.value,
            description = _taskDescription.value,
            category = _selectedCategory.value
        )

        viewModelScope.launch {
            repository.insert(newTask)
            resetForm()
        }
    }

    private fun resetForm() {
        _taskTitle.value = ""
        _taskDescription.value = ""
        _taskPriority.value = TaskPriority.MEDIUM
        _selectedCategory.value = "Personal"
        _isTitleValid.value = false
        _isDescriptionValid.value = false
    }

    fun getTitleErrorMessage(): String? {
        return if (_taskTitle.value.isEmpty()) {
            "Title cannot be empty"
        } else if (_taskTitle.value.length < 3) {
            "Title must be at least 3 characters"
        } else null
    }

    fun getDescriptionErrorMessage(): String? {
        return if (_taskDescription.value.length > 500) {
            "Description must be less than 500 characters"
        } else null
    }
}
