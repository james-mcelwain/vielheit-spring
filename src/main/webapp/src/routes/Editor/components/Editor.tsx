import * as React from 'react'
import './Editor.scss'
import { Layout } from 'antd'
import { EditorProps } from '../containers/EditorContainer'
import EditorMenu from './EditorMenu'
import AbstractionTypeEditor from './AbstractionTypeEditor'

export const Editor = (props: EditorProps) => {
  const selectedItem = props.editorState.selectedItem + ""

  return (
    <div className="editor-container">
      <Layout>
        <Layout.Sider width="0%">
          <EditorMenu/>
        </Layout.Sider>
        <Layout.Content>
          <AbstractionTypeEditor
            submitAbstractionType={props.submitAbstractionType}
            types={props.editorState.types}
            editorState={props.editorState}
            editor={props.editor}/>
        </Layout.Content>
      </Layout>
    </div>
  )
}

export default Editor
