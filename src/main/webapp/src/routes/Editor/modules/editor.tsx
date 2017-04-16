import {Dispatch} from 'redux'
import Entry from '../../../domain/Entry'
import http from '../../../http'
import {AppState} from '../../../store/appState'
import {AbstractModule, AsyncDispatch} from '../../../core/AbstractModule'
import {EntityType} from '../../../domain/EntityType'


export interface EditorState {
  form: string,
  entityTypes: Array<EntityType> | null,
  submitting: boolean,
  error: Error | null
}

export class EditorModule extends AbstractModule<EditorState> {
  public SUBMIT_ENTRY_START = this.Action('SUBMIT_ENTRY_START', (state) => ({...state, error: null, submitting: true}))
  public SUBMIT_ENTRY_SUCCESS = this.Action('SUBMIT_ENTRY_SUCCESS', (state) => ({...state, submitting: false}))
  public SUBMIT_ENTRY_FAIL = this.Action<Error>('SUBMIT_ENTRY_FAIL', (state, payload) => ({
    ...state,
    error: payload,
    submitting: false,
  }))
  public FETCH_ENTITY_TYPES_START = this.Action('FETCH_ENTITY_TYPES_START')
  public FETCH_ENTITY_TYPES_SUCCESS = this.Action<Array<EntityType>>('FETCH_ENTITY_TYPES_SUCCESS', (state, payload) => ({ ...state, entityTypes: payload }))
  public FETCH_ENTITY_TYPES_FAIL = this.Action('FETCH_ENTITY_TYPES_FAIL')

  public EDITOR_FORM_CHANGE = this.Action<string>('EDITOR_FORM_CHANGE', (state, payload) => ({...state, form: payload}))

  @AsyncDispatch
  public getEntityTypes() {
    return async (dispatch: Dispatch<EditorState>, getState: () => AppState) => {
      dispatch(this.FETCH_ENTITY_TYPES_START.dispatch())
      const { data } = await http.get('entity/type')
      await dispatch(this.FETCH_ENTITY_TYPES_SUCCESS.dispatch(data))
    }
  }

  @AsyncDispatch
  public changeForm(formName: string) {
    return (dispatch: Dispatch<EditorState>, getState: () => AppState) => dispatch(this.EDITOR_FORM_CHANGE.dispatch(formName))
  }

  @AsyncDispatch
  public submitEntityType(entityType: EntityType) {
    return async (dispatch: Dispatch<EditorState>, getState: () => AppState) => {
      try {
        dispatch(this.SUBMIT_ENTRY_START.dispatch())
        await http.post('entity/type', entityType)
        dispatch(this.SUBMIT_ENTRY_SUCCESS.dispatch())
      } catch (err) {
        dispatch(this.SUBMIT_ENTRY_FAIL.dispatch(err))
      }
    }
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
  entityTypes: null,
})
