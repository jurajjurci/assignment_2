package sk.jurci.assignment_2

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import sk.jurci.core_datastore.model.Theme
import sk.jurci.core_datastore.repository.IDatastoreRepository
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    datastoreRepository: IDatastoreRepository,
) : ViewModel() {

    val selectedTheme: StateFlow<Theme?> = datastoreRepository.getSelectedTheme()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
            initialValue = null,
        )
}