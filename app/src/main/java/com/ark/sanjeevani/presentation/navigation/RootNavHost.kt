package com.ark.sanjeevani.presentation.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.ark.sanjeevani.presentation.features.auth.screen.LoginScreen
import com.ark.sanjeevani.presentation.features.auth.screen.RegistrationScreen
import com.ark.sanjeevani.presentation.features.individual.doctor.screen.DoctorCategoryScreen
import com.ark.sanjeevani.presentation.features.individual.doctor.screen.DoctorDetailScreen
import com.ark.sanjeevani.presentation.features.individual.doctor.screen.DoctorListScreen
import com.ark.sanjeevani.presentation.features.individual.hospital.screen.HospitalDetailScreen
import com.ark.sanjeevani.presentation.features.individual.hospital.screen.HospitalListScreen
import com.ark.sanjeevani.presentation.features.individual.notification.screen.NotificationScreen
import com.ark.sanjeevani.presentation.features.individual.profile.screen.ProfileScreen
import com.ark.sanjeevani.presentation.features.individual.tab.logic.TabDestinations
import com.ark.sanjeevani.presentation.features.individual.tab.logic.createTabDestination
import com.ark.sanjeevani.presentation.features.individual.tab.logic.getTabDestination
import com.ark.sanjeevani.presentation.features.individual.tab.screen.TabContainer
import com.ark.sanjeevani.presentation.features.onBoarding.screen.LocalizationScreen
import com.ark.sanjeevani.presentation.features.onBoarding.screen.OnboardingScreen
import com.ark.sanjeevani.utils.AnimatedNavHost
import com.ark.sanjeevani.utils.safePopBackStack


@Composable
fun RootNavHost(
    modifier: Modifier = Modifier,
    startDestination: IndividualDestinations
) {
    val navController = rememberNavController()

    AnimatedNavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier.fillMaxSize()
    ) {
        composable<IndividualDestinations.Tab> { navBackStack ->
            val tab = navBackStack.toRoute<IndividualDestinations.Tab>()
            TabContainer(
                navController = navController,
                initialTab = getTabDestination(tab.initialTab)
            )
        }

        composable<IndividualDestinations.Localization> {
            LocalizationScreen(
                onLanguageSelected = {
                    navController.navigate(IndividualDestinations.Onboarding)
                }
            )
        }

        composable<IndividualDestinations.Onboarding> {
            OnboardingScreen(
                onFinishClick = {
                    navController.navigate(IndividualDestinations.Login) {
                        popUpTo(0) { inclusive = true } // clear everything
                        launchSingleTop = true
                    }
                }
            )
        }

        composable<IndividualDestinations.Login> {
            LoginScreen(
                onLoginSuccessfully = {
                    navController.navigate(
                        IndividualDestinations.Tab(
                            createTabDestination(TabDestinations.Home)
                        )
                    ) {
                        popUpTo(0) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }

        composable<IndividualDestinations.Registration> {
            RegistrationScreen(
                onRegCompleted = {
                    navController.navigate(IndividualDestinations.Tab) {
                        popUpTo(0) { inclusive = true }
                        launchSingleTop = true
                    }
                },
                onUserNotAuthenticated = {
                    navController.navigate(IndividualDestinations.Login) {
                        popUpTo(0) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }

        composable<IndividualDestinations.HospitalList> { navBackStack ->
            val hospitalList = navBackStack.toRoute<IndividualDestinations.HospitalList>()
            HospitalListScreen(
                type = hospitalList.type,
                onHospitalClicked = {
                    navController.navigate(IndividualDestinations.HospitalDetail(it))
                },
                onBackClicked = {
                    navController.safePopBackStack()
                }
            )
        }

        composable<IndividualDestinations.HospitalDetail> { navBackStack ->
            val hospitalDetail = navBackStack.toRoute<IndividualDestinations.HospitalDetail>()
            HospitalDetailScreen(
                hospitalId = hospitalDetail.id,
                onBackClicked = {
                    navController.safePopBackStack()
                }
            )
        }

        composable<IndividualDestinations.Notification> {
            NotificationScreen()
        }

        composable<IndividualDestinations.Profile> {
            ProfileScreen()
        }

        composable<IndividualDestinations.DoctorCategory> {
            DoctorCategoryScreen(
                onCategoryClick = { id, cat ->
                    navController.navigate(IndividualDestinations.DoctorList(id, cat))
                },
                onBackClick = {
                    navController.safePopBackStack()
                }
            )
        }

        composable<IndividualDestinations.DoctorList> { backStack ->
            val doctorList = backStack.toRoute<IndividualDestinations.DoctorList>()
            DoctorListScreen(
                categoryId = doctorList.id,
                categoryName = doctorList.cat,
                onDoctorClicked = {
//                    navController.navigate(IndividualDestinations.DoctorDetail)
                },
                onBackClicked = {
                    navController.safePopBackStack()
                }
            )
        }

        composable<IndividualDestinations.DoctorDetail> {
            DoctorDetailScreen(
                doctorId = "",
                onBackClicked = {
                    navController.safePopBackStack()
                }
            )
        }
    }
}
