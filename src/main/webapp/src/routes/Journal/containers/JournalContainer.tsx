import {connect, Dispatch} from 'react-redux'
import journalModule from '../modules/journal'

import {AppState} from '../../../store/appState'
import Journal from '../components/Journal'
import {JournalState} from '../modules/journal'

const mapDispatchToProps = journalModule.getAsyncActions()

const mapStateToProps = (state: AppState) => ({
  journalState: state.journal,
})

export interface JournalProps {
  journalState: JournalState,
  journal: (...args: any[]) => (dispatch: Dispatch<JournalState>) => any
}

export default connect(mapStateToProps, mapDispatchToProps)(Journal)
