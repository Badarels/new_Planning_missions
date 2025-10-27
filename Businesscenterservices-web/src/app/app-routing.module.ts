import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { MainContaintsComponent } from './layouts/main-containts/main-containts.component';

const routes: Routes = [
  // Redirection par défaut vers Accueil
  { path: '', redirectTo: 'Accueil', pathMatch: 'full' },

  // Layout principal
  {
    path: '',
    component: MainContaintsComponent,
    children: [
      // Accueil
      {
        path: 'Accueil',
        loadChildren: () =>
          import('./accueil/accueil.module').then(m => m.AccueilModule)
      },

      // Layout global (barre latérale, header…)
      {
        path: 'main',
        loadChildren: () =>
          import('./layouts/layouts.module').then(m => m.LayoutsModule)
      },

      // Utilisateur
      {
        path: 'listeUtilisateur',
        loadChildren: () =>
          import('./Utilisateur/utilisateur.module').then(m => m.UtilisateurModule),
        data: { title: 'Liste des utilisateurs' }
      },
      { path: 'ajoutUtilisateur', redirectTo: 'listeUtilisateur/ajout', pathMatch: 'full' },
      { path: 'ModifierUtilisateur', redirectTo: 'listeUtilisateur/edit', pathMatch: 'full' },
      { path: 'ProfilUtilisateur', redirectTo: 'listeUtilisateur/profil', pathMatch: 'full' },

      // Médecin
      {
        path: 'medecin',
        loadChildren: () =>
          import('./Medecin/medecin.module').then(m => m.MedecinModule)
      },

      // Centre hospitalier
      {
        path: 'list-CentreHospitalier',
        loadChildren: () =>
          import('./centreHospitalier/centre-hospitalier.module').then(
            m => m.CentreHospitalierModule
          ),
        data: { title: 'Liste des centres hospitaliers' }
      },
      { path: 'ajoutCentreHospitalier', redirectTo: 'list-CentreHospitalier/ajout', pathMatch: 'full' },
      { path: 'editCentreHospitalier/:id', redirectTo: 'list-CentreHospitalier/edit/:id', pathMatch: 'full' },

      // Mission
      {
        path: 'mission',
        loadChildren: () =>
          import('./missions/mission.module').then(m => m.MissionModule)
      },

      // Planning
      {
        path: 'planningMission',
        loadChildren: () =>
          import('./plannings/planning.module').then(m => m.PlanningModule)
      },

      // Employé
      {
        path: 'employe',
        loadChildren: () =>
          import('./Employe/employe.module').then(m => m.EmployeModule)
      }
    ]
  },

  // Authentification
  {
    path: 'login',
    loadChildren: () =>
      import('./authentification/authetification.module').then(m => m.AuthetificationModule)
  },

  // Redirection si route non trouvée
  { path: '**', redirectTo: 'Accueil' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { scrollPositionRestoration: 'enabled' })],
  exports: [RouterModule]
})
export class AppRoutingModule {}
