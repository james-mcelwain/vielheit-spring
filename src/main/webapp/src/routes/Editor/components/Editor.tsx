import * as React from 'react'
import './Editor.scss'
import { Layout } from 'antd'
import { EditorProps } from '../containers/EditorContainer'
import EditorMenu from './EditorMenu'
import AbstractionTypeEditor from './AbstractionTypeEditor'
import AbstractionEditor from './AbstractionEditor'

export interface EditorState {
  editor: () => JSX.Element
}

export class Editor extends React.Component<EditorProps, EditorState> {
  public AbstractionType = () => <AbstractionTypeEditor
    submitAbstractionType={this.props.submitAbstractionType}
    types={this.props.editorState.types}
    editorState={this.props.editorState}
    editor={this.props.editor}/>

  public Abstraction = () => <AbstractionEditor
    types={this.props.editorState.types}/>

  public Entry = () => <div>STUB ENTRY</div>

  public selectEditor(editor: 'Abstraction' | 'AbstractionType' | 'Entry') {
    this.setState({
      editor: this[editor],
    })
  }

  public state = {
    editor: this.AbstractionType
  }

  public render() {
    const selectedItem = this.props.editorState.selectedItem + ''
    const Editor = this.state.editor

    return (
      <div className="editor-container">
        <Layout>
          <Layout.Sider width="0%">
            <EditorMenu selectEditor={this.selectEditor.bind(this)}/>
          </Layout.Sider>
          <Layout.Content>
            <Editor/>
          </Layout.Content>
        </Layout>
      </div>
    )
  }
}

export default Editor
