import {AbstractModule, AsyncDispatch} from 'core/AbstractModule'
import {Dispatch} from 'redux'
import http from '../../../http'

export interface GetEntriesRequest {

}

export interface JournalState {
  fetching: boolean
  entries: any[]
  error: Error | null
}

export class JournalModule extends AbstractModule<JournalState> {
  public FETCH_ENTRIES_START = this.Action('FETCH_ENTRIES_START')
  public FETCH_ENTRIES_SUCCESS = this.Action<any>('FETCH_ENTRIES_SUCCESS', (s, entries) => ({...s, fetching: true, entries }))
  public FETCH_ENTRIES_FAIL = this.Action<Error>('FETCH_ENTRIES_FAIL', (s, error) => ({ ...s, fetching: false, error }))

  @AsyncDispatch
  public getEntries() {
    return async (dispatch: Dispatch<JournalState>) => {
      dispatch(this.FETCH_ENTRIES_START.dispatch())
      try {
        const entries = await http.get(`journal/${this.userId()}`)
        dispatch(this.FETCH_ENTRIES_SUCCESS.dispatch(entries))
      } catch (e) {
        dispatch(this.FETCH_ENTRIES_FAIL.dispatch(e))
      }
    }
  }

}

export default new JournalModule({
  error: null,
  entries: [],
  fetching: false,
})
