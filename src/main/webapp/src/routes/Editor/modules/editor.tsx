import { AbstractModule, AsyncDispatch } from 'core/AbstractModule'
import { Dispatch } from 'redux'
import http from 'http'

export interface EditorState {
  error: Error | null
  submitting: boolean
  types: AbstractionType[]
}

export interface AbstractionType {
  type: string
  description: string
}

export class EditorModule extends AbstractModule<EditorState> {
  public EDITOR_SUBMIT_START =
    this.Action('EDITOR_SUBMIT_START', (state) => ({...state, error: null, submitting: true}))
  public EDIT_SUBMIT_SUCCESS =
    this.Action('EDITOR_SUBMIT_SUCCESS', (state) => ({...state, error: null, submitting: false}))
  public EDIT_SUBMIT_FAIL =
    this.Action<Error>('EDITOR_SUBMIT_FAIL', (state, payload) => ({...state, error: payload, submitting: false}))

  @AsyncDispatch
  public submitAbstractionType(type: AbstractionType) {
    return async (dispatch: Dispatch<EditorState>) => {
      dispatch(this.EDITOR_SUBMIT_START.dispatch())
      try {
        await http.post(`journal/type`, type)
        dispatch(this.EDIT_SUBMIT_SUCCESS.dispatch())
      } catch(e) {
        dispatch((this.EDIT_SUBMIT_FAIL.dispatch(e)))
      }
    }
  }
}

export default new EditorModule({
  error: null,
  submitting: false,
  types: []
})
