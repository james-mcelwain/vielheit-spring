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

}

export default new JournalModule({
  error: null,
  entries: [],
  fetching: false,
})
