import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TopRatedComponent } from './components/top-rated/top-rated.component';
import { RouterModule, Routes} from '@angular/router';
import { WeeklyTopComponent } from './components/weekly-top/weekly-top.component';
import { MostViewComponent } from './components/most-view/most-view.component';
import { LoginComponent } from './components/form/login/login.component';
import { RegistrationComponent } from './components/form/registration/registration.component';
import { MymusicComponent } from './components/mymusic/mymusic.component';
import { AdminComponent } from './components/admin/admin.component';


const routes :Routes = [
{path: '', redirectTo:'/toprated',pathMatch:'full'},
{path: 'toprated', component:TopRatedComponent},
{path: 'Weeklytop', component:WeeklyTopComponent },
{path: 'mostview', component:MostViewComponent},
{path: 'login', component:LoginComponent},
{path: 'register', component:RegistrationComponent},
{path: 'mymusic', component:MymusicComponent},
{path: 'admin', component:AdminComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes),
  CommonModule
  ],
  exports:[RouterModule],
  declarations: []
})
export class AppRoutingModule { }
