package com.example.helloandroidagain.data.repository.local

import androidx.room.withTransaction
import com.example.helloandroidagain.data.db.LogoDao
import com.example.helloandroidagain.data.db.StorageDatabase
import com.example.helloandroidagain.data.db.TournamentDao
import com.example.helloandroidagain.data.db.entity.LogoEntity
import com.example.helloandroidagain.data.db.entity.TournamentEntity
import com.example.helloandroidagain.data.mapper.LogoMapper
import com.example.helloandroidagain.data.mapper.TournamentMapper
import com.example.helloandroidagain.data.model.Tournament
import com.example.helloandroidagain.data.model.TournamentLogo
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.mockkStatic
import io.mockk.slot
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class TournamentRepositoryImplTest {

    companion object {
        private const val TOURNAMENT_ID = 11L
        private const val LOGO_ID = "99"
    }

    @RelaxedMockK
    lateinit var tournamentDao: TournamentDao

    @RelaxedMockK
    lateinit var logoDao: LogoDao

    @MockK
    lateinit var storageDatabase: StorageDatabase

    @InjectMockKs
    lateinit var underTest: TournamentRepositoryImpl

    @Before
    fun init() {
        MockKAnnotations.init(this)
        mockkObject(TournamentMapper)
        mockkObject(LogoMapper)
        mockkStatic(
            "androidx.room.RoomDatabaseKt"
        )


    }

    @Test
    fun shouldGetTournamentsInfoPopulatedWithLogo() = runTest {
        val tournaments = listOf(
            mockk<TournamentEntity> {
                every { logoId } returns LOGO_ID
            }
        )
        val tournamentDomain = mockk<Tournament> {}
        val logo = mockk<LogoEntity> {}
        val logoDomain = mockk<TournamentLogo> {}

        every { tournamentDao.getAllTournaments() } returns flowOf(tournaments)
        coEvery { logoDao.getLogoById(LOGO_ID) } returns logo
        every { LogoMapper.toDomain(logo) } returns logoDomain
        every { TournamentMapper.toDomain(tournaments[0], logoDomain) } returns tournamentDomain

        val result = underTest.getTournaments().first()

        assertEquals(listOf(tournamentDomain), result)
    }

    @Test
    fun shouldAddNewTournamentWithLogo() = runTest {
        val logoDomain = mockk<TournamentLogo> {}
        val tournamentDomain = mockk<Tournament> {
            every { logo } returns logoDomain
        }
        val tournamentData = mockk<TournamentEntity> {}
        val logoData = mockk<LogoEntity> {}
        every { TournamentMapper.toData(tournamentDomain) } returns tournamentData
        every { LogoMapper.toData(logoDomain) } returns logoData
        mockRoomExtendedFunction()

        underTest.addTournament(tournamentDomain)

        coVerify { tournamentDao.insertTournament(tournamentData) }
        coVerify { logoDao.insertLogo(logoData) }
    }

    @Test
    fun shouldPassIdForRemoval() = runTest {
        underTest.removeTournament(TOURNAMENT_ID)

        coVerify { tournamentDao.deleteTournamentById(TOURNAMENT_ID) }
    }

    private fun mockRoomExtendedFunction() {
        val transactionLambda = slot<suspend () -> Unit>()
        coEvery { storageDatabase.withTransaction(capture(transactionLambda)) } coAnswers {
            transactionLambda.captured.invoke()
        }
    }
}
