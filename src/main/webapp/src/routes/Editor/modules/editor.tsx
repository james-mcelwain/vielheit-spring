// ------------------------------------
// Constants
// ------------------------------------

import {Dispatch} from 'redux'
import Entry from '../../../domain/Entry'
import http from '../../../http'
import {AppAction} from '../../../store/appAction'
import {AppState} from '../../../store/appState'
import makeConstant from '../../../store/makeConstant'
import {AbstractModule} from '../../../core/AbstractModule'

const SUBMIT_ENTRY_START =  makeConstant('SUBMIT_ENTRY_START')
const SUBMIT_ENTRY_SUCCESS = makeConstant('SUBMIT_ENTRY_SUCCESS')
const SUBMIT_ENTRY_FAIL = makeConstant('SUBMIT_ENTRY_FAIL')

const EDITOR_FORM_CHANGE = makeConstant('EDITOR_FORM_CHANGE')

// ------------------------------------
// Actions
// ------------------------------------
export const submit = (entry: Entry) => (dispatch: Dispatch<EditorState>, getState: () => AppState) =>
  dispatch({ type: SUBMIT_ENTRY_START }) &&
  http.post('entry', entry)
    .then(() => dispatch({ type: SUBMIT_ENTRY_SUCCESS() }))
    .catch(() => dispatch({ type: SUBMIT_ENTRY_FAIL() }))

export const changeForm = (formName: string) => (dispatch: Dispatch<EditorState>, getState: () => AppState) =>
  dispatch({ type: EDITOR_FORM_CHANGE(), payload: formName })

export const actions = {
  submit,
  changeForm,
}

// ------------------------------------
// Action Handlers
// ------------------------------------
export interface EditorState {
  form: string,
  submitting: boolean,
  error: Error | null
}

class EditorModule extends AbstractModule<EditorModule, EditorState> {
  public state: EditorState;
}

const module = new EditorModule({
  form: 'entry',
  submitting: false,
  error: null,
})
  .addAction(SUBMIT_ENTRY_START, (state) => ({ ...state, error: null, submitting: true }))
  .addAction(SUBMIT_ENTRY_SUCCESS, (state) => ({ ...state, submitting: false }))
  .addAction<Error>(SUBMIT_ENTRY_FAIL, (state, { payload }) => ({ ...state, error: payload instanceof Error ? payload : null, submitting: false }))
  .addAction(EDITOR_FORM_CHANGE, (state, action) => ({ ...state, form: action.payload }))

export default function registerReducer(state = initialState, action: AppAction<EditorState>) {
  const handler = ACTION_HANDLERS[action.type]

  return handler ? handler(state, action) : state
}
