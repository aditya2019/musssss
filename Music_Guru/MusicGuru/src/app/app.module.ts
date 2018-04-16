import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';


import { AppComponent } from './app.component';
import { FooterComponent } from './components/shared/footer/footer.component';
import { HeaderComponent } from './components/shared/header/header.component';
import { SliderComponent } from './components/shared/slider/slider.component';
import { MidheaderComponent } from './components/shared/midheader/midheader.component';
import { LoginComponent } from './components/form/login/login.component';
import { RegistrationComponent } from './components/form/registration/registration.component';
import { SongCardComponent } from './components/shared/song-card/song-card.component';
import { TopRatedComponent } from './components/top-rated/top-rated.component';
import { WeeklyTopComponent } from './components/weekly-top/weekly-top.component';
import { MostViewComponent } from './components/most-view/most-view.component';
import { AppRoutingModule } from './/app-routing.module';
import { MymusicComponent } from './components/mymusic/mymusic.component';

@NgModule({
  declarations: [
    AppComponent,
    FooterComponent,
    HeaderComponent,
    SliderComponent,
    MidheaderComponent,
    LoginComponent,
    RegistrationComponent,
    SongCardComponent,
    TopRatedComponent,
    WeeklyTopComponent,
    MostViewComponent,
    MymusicComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
