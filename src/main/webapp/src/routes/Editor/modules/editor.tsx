import { AbstractModule, AsyncDispatch } from 'core/AbstractModule'
import { Dispatch } from 'redux'
import http from 'http'
import * as React from 'react'
import { store } from 'main'

export interface EditorState {
  error: Error | null
  submitting: boolean
  types: AbstractionType[]
  fetching: boolean
  form: 'type' | 'abstraction' | 'entry'
  selectedItem: number
}

export interface AbstractionType {
  id?: number
  type: string
  description: string
}

export interface Abstraction {
  id?: number
  name: string
  description: string
  type: AbstractionType
}

export interface UserGraph {
  types: AbstractionType[]
}

export class EditorModule extends AbstractModule<EditorState> {
  public EDITOR_SUBMIT_START =
    this.Action('EDITOR_SUBMIT_START', (state) => ({...state, submitting: true}))
  public EDIT_SUBMIT_SUCCESS =
    this.Action('EDITOR_SUBMIT_SUCCESS', (state) => ({...state, submitting: false}))
  public EDIT_SUBMIT_FAIL =
    this.Action<Error>('EDITOR_SUBMIT_FAIL', (state, payload) => ({...state, error: payload, submitting: false}))

  public FETCH_TYPES_START =
    this.Action('FETCH_TYPES_START', (state) => ({...state, fetching: true}))
  public FETCH_TYPES_SUCCESS =
    this.Action<AbstractionType[]>('FETCH_TYPES_SUCCESS', (state, payload) => ({
      ...state,
      types: payload,
      fetching: false,
    }))
  public FETCH_TYPES_FAIL =
    this.Action<Error>('FETCH_TYPES_FAIL', (state, payload) => ({...state, error: payload, fetching: false}))

  public EDITOR_RESET_ERROR = this.Action('EDITOR_RESET_ERROR', (state) => ({...state, error: null}))

  public EDITOR_SELECT_ITEM = this.Action<number>('EDITOR_SELECT_ITEM', (state, item) => console.log('select', item) || ({
    ...state,
    selectedItem: item,
  }))

  @AsyncDispatch
  public fetchUserData() {
    return async (dispatch: Dispatch<EditorState>) => {
      dispatch(this.FETCH_TYPES_START.dispatch())
      try {
        const types = await http.get('journal/types').then((r) => r.data)
        setTimeout(() => dispatch(this.FETCH_TYPES_SUCCESS.dispatch(types)))
      } catch (e) {
        dispatch(this.FETCH_TYPES_FAIL.dispatch(e))
      }

    }
  }

  @AsyncDispatch
  public submitAbstractionType(type: AbstractionType) {
    this.resetError()
    return async (dispatch: Dispatch<EditorState>) => {
      dispatch(this.EDITOR_SUBMIT_START.dispatch())
      try {
        await http.post(`journal/type`, type)
        dispatch(this.EDIT_SUBMIT_SUCCESS.dispatch())
      } catch (e) {
        const error = e.response.data.message

        dispatch((this.EDIT_SUBMIT_FAIL.dispatch(error)))
      }

      try {
        dispatch(this.FETCH_TYPES_START.dispatch())
        const types: AbstractionType[] = await http.get('journal/types').then(r => r.data)
        dispatch(this.FETCH_TYPES_SUCCESS.dispatch(types))

        // const idx = types.findIndex(t => t.type === type.type && t.description === type.description)
        //
        // // "new" is always idx 0
        // dispatch(this.EDITOR_SELECT_ITEM.dispatch(idx + 1))
      } catch (e) {
        dispatch(this.FETCH_TYPES_FAIL.dispatch(e))
      }
    }
  }

  public selectItem(idx: number) {
    store.dispatch(this.EDITOR_SELECT_ITEM.dispatch(idx))
  }

  public resetError() {
    store.dispatch(this.EDITOR_RESET_ERROR.dispatch())
  }
}

export default new EditorModule({
  error: null,
  submitting: false,
  types: [],
  selectedItem: 0,
  fetching: false,
  form: 'type',
})
