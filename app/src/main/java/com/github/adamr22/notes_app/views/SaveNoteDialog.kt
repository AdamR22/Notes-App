package com.github.adamr22.notes_app.views

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Bitmap
import android.graphics.pdf.PdfDocument
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import com.github.adamr22.notes_app.R
import com.github.adamr22.notes_app.model.Note
import com.github.adamr22.notes_app.viewmodels.WriteEditNoteViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.FileOutputStream


@AndroidEntryPoint
class SaveNoteDialog(
    private val bitmap: Bitmap,
    private val noteInstance: Note,
    private val isEdited: Boolean,
    private val fa: FragmentManager,
) : DialogFragment() {

    private val viewModel by viewModels<WriteEditNoteViewModel>()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setMessage(getString(R.string.save_note_dialog_content))
            .setPositiveButton(getString(R.string.yes)) { _, _ ->

                viewModel.saveNote(noteInstance)

                val pdfDocument = PdfDocument()
                val pageInfo = PdfDocument.PageInfo.Builder(595, 842, 1).create()

                val page = pdfDocument.startPage(pageInfo)
                page.canvas.drawBitmap(bitmap, 0F, 0F, null)
                pdfDocument.finishPage(page)

                val filePath =
                    File(Environment.getExternalStorageDirectory(), "${noteInstance.title}.pdf")
                pdfDocument.writeTo(FileOutputStream(filePath))

                pdfDocument.close()

                Toast.makeText(
                    requireContext(),
                    getString(R.string.note_saved),
                    Toast.LENGTH_SHORT
                ).show()
                fa.popBackStack()
            }
            .setNegativeButton(getString(R.string.no)) { _, _ ->
                if (isEdited) {
                    viewModel.updateNote(
                        title = noteInstance.title,
                        content = noteInstance.content,
                        noteId = noteInstance.id!!,
                        noteImage = noteInstance.image
                    )

                } else {
                    viewModel.saveNote(noteInstance)

                    Toast.makeText(
                        requireContext(),
                        getString(R.string.note_edited),
                        Toast.LENGTH_SHORT
                    ).show()

                    fa.popBackStack()
                }
            }
            .create()
    }

    companion object {
        const val TAG = "SaveNoteDialog"
    }
}