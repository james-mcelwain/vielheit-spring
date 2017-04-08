import {Action} from "redux"
export type ActionType =  string

export interface ActionConstant {
  (): ActionType
  compare(a: Action): boolean
}

export default function makeConstant(constantString: ActionType): ActionConstant {
  return Object.assign(() => {
    return constantString
  }, { compare(a: Action) { return a.type  === constantString } })
}
