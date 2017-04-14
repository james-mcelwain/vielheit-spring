// ------------------------------------
// Constants
// ------------------------------------

import {Dispatch} from 'redux'
import Entry from '../../../domain/Entry'
import http from '../../../http'
import {DispatchedAction} from '../../../store/DispatchedAction'
import {AppState} from '../../../store/appState'
import makeConstant from '../../../store/Action'
import {AbstractModule, AsyncDispatch} from '../../../core/AbstractModule'
import {Action} from '../../../store/Action'

const SUBMIT_ENTRY_START =  new Action('SUBMIT_ENTRY_START', (state) => ({ ...state, error: null, submitting: true }))
const SUBMIT_ENTRY_SUCCESS = new Action('SUBMIT_ENTRY_SUCCESS', (state) => ({ ...state, submitting: false }))
const SUBMIT_ENTRY_FAIL = new Action('SUBMIT_ENTRY_FAIL',  (state, { payload }) => ({ ...state, error: payload instanceof Error ? payload : null, submitting: false }))
const EDITOR_FORM_CHANGE = new Action('EDITOR_FORM_CHANGE', (state, action) => ({ ...state, form: action.payload }))

export interface EditorState {
  form: string,
  submitting: boolean,
  error: Error | null
}

class EditorModule extends AbstractModule<EditorState> {

  @AsyncDispatch
  public changeForm(formName: string) {
    return (dispatch: Dispatch<EditorState>, getState: () => AppState) => dispatch({type: EDITOR_FORM_CHANGE(), payload: formName})
  }

  @AsyncDispatch
  public submit(entry: Entry) {
    return async (dispatch: Dispatch<EditorState>, getState: () => AppState) => {
      dispatch({type: SUBMIT_ENTRY_START})
      try {
        await http.post('entry', entry)
        dispatch(SUBMIT_ENTRY_SUCCESS.dispatch())
      } catch (err) {
        dispatch(SUBMIT_ENTRY_FAIL(err))
      }
    }
  }

}

export default new EditorModule({
  form: 'entry',
  submitting: false,
  error: null,
})
  .addAction(SUBMIT_ENTRY_START)
  .addAction(SUBMIT_ENTRY_SUCCESS)
  .addAction(SUBMIT_ENTRY_FAIL)
  .addAction(EDITOR_FORM_CHANGE)
  .toReducer()
