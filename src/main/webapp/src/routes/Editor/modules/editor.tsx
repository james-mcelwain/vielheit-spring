import {Dispatch} from 'redux'
import Entry from '../../../domain/Entry'
import http from '../../../http'
import {AppState} from '../../../store/appState'
import {AbstractModule, AsyncDispatch} from '../../../core/AbstractModule'


export interface EditorState {
  form: string,
  submitting: boolean,
  error: Error | null
}

class EditorModule extends AbstractModule<EditorState> {
  public SUBMIT_ENTRY_START = this.Action('SUBMIT_ENTRY_START', (state) => ({...state, error: null, submitting: true}))
  public SUBMIT_ENTRY_SUCCESS = this.Action('SUBMIT_ENTRY_SUCCESS', (state) => ({...state, submitting: false}))
  public SUBMIT_ENTRY_FAIL = this.Action<Error>('SUBMIT_ENTRY_FAIL', (state, payload) => ({
    ...state,
    error: payload,
    submitting: false,
  }))
  public EDITOR_FORM_CHANGE = this.Action<string>('EDITOR_FORM_CHANGE', (state, payload) => ({...state, form: payload}))

  @AsyncDispatch
  public changeForm(formName: string) {
    return (dispatch: Dispatch<EditorState>, getState: () => AppState) => dispatch(this.EDITOR_FORM_CHANGE.dispatch(formName))
  }

  @AsyncDispatch
  public submitEntry(entry: Entry) {
    return async (dispatch: Dispatch<EditorState>, getState: () => AppState) => {
      dispatch(this.SUBMIT_ENTRY_START.dispatch())
      try {
        await http.post('entry', entry)
        dispatch(this.SUBMIT_ENTRY_SUCCESS.dispatch())
      } catch (err) {
        dispatch(this.SUBMIT_ENTRY_FAIL.dispatch(err))
      }
    }
  }
}

export default new EditorModule({
  form: 'entry',
  submitting: false,
  error: null,
})
