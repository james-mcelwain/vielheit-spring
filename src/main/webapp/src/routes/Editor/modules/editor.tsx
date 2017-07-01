import {Dispatch} from 'redux'
import Entry from '../../../domain/Entry'
import http from '../../../http'
import {AbstractModule, AsyncDispatch} from '../../../core/AbstractModule'
import {AbstractionType} from '../../../domain/AbstractionType'
import {Abstraction} from '../../../domain/Abstraction'

export interface EditorState {
  form: string,
  abstractionTypes: Array<AbstractionType>,
  submitting: boolean,
  error: Error | null
}

export class EditorModule extends AbstractModule<EditorState> {
  public SUBMIT_ENTRY_START = this.Action('SUBMIT_ENTRY_START', (state) => ({...state, error: null, submitting: true}))
  public SUBMIT_ENTRY_SUCCESS = this.Action('SUBMIT_ENTRY_SUCCESS', (state) => ({...state, submitting: false }))
  public SUBMIT_ENTRY_FAIL = this.Action<Error>('SUBMIT_ENTRY_FAIL', (state, payload) => ({
    ...state,
    error: payload,
    submitting: false,
  }))
  public FETCH_ABSTRACTION_TYPES_START = this.Action('FETCH_ABSTRACTION_TYPES_START')
  public FETCH_ABSTRACTION_TYPES_SUCCESS = this.Action<Array<AbstractionType>>('FETCH_ABSTRACTION_TYPES_SUCCESS', (state, payload) => ({ ...state, abstractionTypes: payload }))
  public FETCH_ABSTRACTION_TYPES_FAIL = this.Action('FETCH_ABSTRACTION_TYPES_FAIL')

  public SUBMIT_ABSTRACTION_TYPE_START = this.Action('SUBMIT_ABSTRACTION_TYPE_START')
  public SUBMIT_ABSTRACTION_TYPE_SUCCESS = this.Action('SUBMIT_ABSTRACTION_TYPE_SUCCESS')
  public SUBMIT_ABSTRACTION_TYPE_FAIL = this.Action('SUBMIT_ABSTRACTION_TYPE_FAIL')

  public SUBMIT_ABSTRACTION_START = this.Action('SUBMIT_ABSTRACTION_START')
  public SUBMIT_ABSTRACTION_SUCCESS = this.Action('SUBMIT_ABSTRACTION_SUCCESS')
  public SUBMIT_ABSTRACTION_FAIL = this.Action('SUBMIT_ABSTRACTION_FAIL')

  public EDITOR_FORM_CHANGE = this.Action<string>('EDITOR_FORM_CHANGE', (state, payload) => ({...state, form: payload}))

  @AsyncDispatch
  public getAbstractionTypes() {
    return async (dispatch: Dispatch<EditorState>) => {
      dispatch(this.FETCH_ABSTRACTION_TYPES_START.dispatch())
      const { data } = await http.get('abstraction/type')
      await dispatch(this.FETCH_ABSTRACTION_TYPES_SUCCESS.dispatch(data))
    }
  }

  @AsyncDispatch
  public changeForm(formName: string) {
    return (dispatch: Dispatch<EditorState>) => dispatch(this.EDITOR_FORM_CHANGE.dispatch(formName))
  }

  @AsyncDispatch
  public submitAbstractionType(abstractionType: AbstractionType) {
    return async (dispatch: Dispatch<EditorState>) => {
      try {
        dispatch(this.SUBMIT_ABSTRACTION_TYPE_START.dispatch())
        await http.post(`${this.userId()}/abstraction/type`, abstractionType)
        this.message('success', 'Abstraction type submitted!')
        dispatch(this.SUBMIT_ABSTRACTION_TYPE_SUCCESS.dispatch())
        this.getAbstractionTypes()(dispatch)
      } catch (err) {
        dispatch(this.SUBMIT_ABSTRACTION_TYPE_FAIL.dispatch(err))
      }
    }
  }

  @AsyncDispatch
  public submitAbstraction(abstraction: Abstraction) {
    return async (dispatch: Dispatch<EditorState>) => {
      try {
        dispatch(this.SUBMIT_ABSTRACTION_START.dispatch())
        await http.post(`${this.userId()}/abstraction`, abstraction)
        this.message('success', 'Abstraction submitted!')
        dispatch(this.SUBMIT_ABSTRACTION_SUCCESS.dispatch())
      } catch (err) {
        dispatch(this.SUBMIT_ABSTRACTION_FAIL.dispatch(err))
      }
    }
  }

  @AsyncDispatch
  public submitEntry(entry: Entry) {
    return async (dispatch: Dispatch<EditorState>) => {
      dispatch(this.SUBMIT_ENTRY_START.dispatch())
      try {
        await http.post(`${this.userId()}/entry`, entry)
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
  abstractionTypes: [],
})
