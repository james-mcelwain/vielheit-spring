package cc.kojeve.vielheit.dto

import cc.kojeve.vielheit.domain.Entry

class EntryData(entry: Entry) : Dto<Entry>(entry) {
    val title = entry.title
}