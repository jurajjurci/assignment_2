package sk.jurci.feature_settings.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import sk.jurci.core_datastore.model.Theme
import sk.jurci.core_datastore.repository.IDatastoreRepository
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val datastoreRepository: IDatastoreRepository,
) : ViewModel() {

    private val storedTheme = datastoreRepository.getSelectedTheme()
    private val selectedTheme = MutableStateFlow<Theme?>(null)
    val currentTheme = combine(selectedTheme, storedTheme) { selectedTheme, storedTheme ->
        selectedTheme ?: storedTheme
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
        initialValue = Theme.Auto,
    )

    fun setSelectedTheme(theme: Theme) {
        selectedTheme.tryEmit(theme)
    }

    fun saveSelectedTheme(theme: Theme) {
        viewModelScope.launch {
            datastoreRepository.saveSelectedTheme(theme)
        }
    }
}