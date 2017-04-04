// ------------------------------------
// Constants
// ------------------------------------

const SUBMIT_ENTRY_START = 'SUBMIT_ENTRY_START'
const SUBMIT_ENTRY_SUCCESS = 'SUBMIT_ENTRY_SUCCESS'
const SUBMIT_ENTRY_FAIL = 'SUBMIT_ENTRY_FAIL'

// ------------------------------------
// Actions
// ------------------------------------
export const submit = (entry) => (dispatch, getState) =>
  dispatch({ type: SUBMIT_ENTRY_START }) &&
  http.post('entry', entry)
    .then(() => dispatch({ type: SUBMIT_ENTRY_SUCCESS }))
    .catch(() => dispatch({ type: SUBMIT_ENTRY_FAIL }))

export const actions = {
  submit
}

// ------------------------------------
// Action Handlers
// ------------------------------------
const ACTION_HANDLERS = {
  [SUBMIT_ENTRY_START]: (state, action) => ({ ...state, error: null, submitting: true }),
  [SUBMIT_ENTRY_SUCCESS]: (state, action) => ({ ...state, submitting: false }),
  [SUBMIT_ENTRY_FAIL]: (state, action) => ({ ...state, submitting: false, error: action.payload })
}

// ------------------------------------
// Reducer
// ------------------------------------
const initialState = {
  submitting: false,
  error: null
}

export default function registerReducer(state = initialState, action) {
  const handler = ACTION_HANDLERS[action.type]

  return handler ? handler(state, action) : state
}
