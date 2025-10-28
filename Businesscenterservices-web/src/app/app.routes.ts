import { Routes } from '@angular/router';
import { MainContaintsComponent } from './layouts/main-containts/main-containts.component';

export const routes: Routes = [
  { path: '', redirectTo: 'Accueil', pathMatch: 'full' },
  {
    path: '',
    component: MainContaintsComponent,
    children: [
      {
        path: 'Accueil',
        loadChildren: () => import('./accueil/accueil.module').then(m => m.AccueilModule)
      },
      {
        path: 'main',
        loadChildren: () => import('./layouts/layouts.module').then(m => m.LayoutsModule)
      },
      {
        path: 'listeUtilisateur',
        loadChildren: () => import('./Utilisateur/utilisateur.module').then(m => m.UtilisateurModule),
        data: { title: 'Liste des utilisateurs' }
      },
      { path: 'ajoutUtilisateur', redirectTo: 'listeUtilisateur/ajout', pathMatch: 'full' },
      { path: 'ModifierUtilisateur', redirectTo: 'listeUtilisateur/edit', pathMatch: 'full' },
      { path: 'ProfilUtilisateur', redirectTo: 'listeUtilisateur/profil', pathMatch: 'full' },
      {
        path: 'medecin',
        loadChildren: () => import('./Medecin/medecin.module').then(m => m.MedecinModule)
      },
      {
        path: 'list-CentreHospitalier',
        loadChildren: () => import('./centreHospitalier/centre-hospitalier.module').then(m => m.CentreHospitalierModule),
        data: { title: 'Liste des centres hospitaliers' }
      },
      { path: 'ajoutCentreHospitalier', redirectTo: 'list-CentreHospitalier/ajout', pathMatch: 'full' },
      { path: 'editCentreHospitalier/:id', redirectTo: 'list-CentreHospitalier/edit/:id', pathMatch: 'full' },
      {
        path: 'mission',
        loadChildren: () => import('./missions/mission.module').then(m => m.MissionModule)
      },
      {
        path: 'planningMission',
        loadChildren: () => import('./plannings/planning.module').then(m => m.PlanningModule)
      },
      {
        path: 'employe',
        loadChildren: () => import('./Employe/employe.module').then(m => m.EmployeModule)
      }
    ]
  },
  {
    path: 'login',
    loadChildren: () => import('./authentification/authetification.module').then(m => m.AuthetificationModule)
  },
  { path: '**', redirectTo: 'Accueil' }
];
