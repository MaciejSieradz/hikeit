package com.example.hikeit.trails.presentation.models

import com.example.hikeit.trails.domain.Comment
import junit.framework.TestCase.assertEquals
import org.junit.Test

class CommentUiTest {

    @Test
    fun comment_toUserCommentUi_correctMapping() {
        val comment = Comment(
            userAvatarUrl = "123",
            username = "Test",
            rating = 4,
            commentDate = "1 miesiąc temu",
            comment = "Test comment"
        )

        val commentUI = UserCommentUi(
            userAvatarUrl = "123",
            username = "Test",
            rating = 4,
            commentDate = "1 miesiąc temu",
            comment = "Test comment"
        )

        assertEquals(commentUI, comment.toUserCommentUi())
    }
}