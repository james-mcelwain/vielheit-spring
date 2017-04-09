// ------------------------------------
// Constants
// ------------------------------------

import {Dispatch} from 'redux'
import Entry from '../../../domain/Entry'
import http from '../../../http'
import {AppAction} from '../../../store/appAction'
import {AppState} from '../../../store/appState'
import makeConstant from '../../../store/makeConstant'

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
const ACTION_HANDLERS: { [key: string]: (state: EditorState, action: AppAction<EditorState>) => EditorState } = {
  [SUBMIT_ENTRY_START()]: (state, action) => ({ ...state, error: null, submitting: true } as EditorState),
  [SUBMIT_ENTRY_SUCCESS()]: (state, action) => ({ ...state, submitting: false } as EditorState),
  [SUBMIT_ENTRY_FAIL()]: (state, action) => ({ ...state, submitting: false, error: action.payload } as EditorState),
  [EDITOR_FORM_CHANGE()]: (state, action) => ({ ...state, form: action.payload } as EditorState),
}

// ------------------------------------
// Reducer
// ------------------------------------
export interface EditorState {
  form: string,
  submitting: boolean,
  error: Error | null
}
const initialState = {
  form: 'entry',
  submitting: false,
  error: null,
}

export default function registerReducer(state = initialState, action: AppAction<EditorState>) {
  const handler = ACTION_HANDLERS[action.type]

  return handler ? handler(state, action) : state
}
