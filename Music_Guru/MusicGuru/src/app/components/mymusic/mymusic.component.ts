import { Component, OnInit } from '@angular/core';
import { Router } from "@angular/router";
import { SongApiService } from '../../services/song-api.service';
import { AppConfig }from '../../config/config.constant';

@Component({
  selector: 'app-mymusic',
  templateUrl: './mymusic.component.html',
  styleUrls: ['./mymusic.component.css'],
  providers:[SongApiService]
})
export class MymusicComponent implements OnInit {
  public mysong: any=[];
  public flag ='mysong';
  constructor(private songApiService: SongApiService,private router: Router) { }

  ngOnInit() {
    this.getAllMySong();
  }

  getAllMySong(){
    this.songApiService.getAllMySong().subscribe((res) =>{
      this.mysong = res;
      console.log(this.mysong+"hello");
    },(error:any)=>{
   console.log(error);
    })
  }


  }
