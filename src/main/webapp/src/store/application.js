export default function (state = { errors: [] }, action) {
  if (action.type === RESPONSE_ERROR) {
    state.errors.push(action.payload)
  }
  return state
}

export const RESPONSE_ERROR = 'RESPONSE_ERROR'
