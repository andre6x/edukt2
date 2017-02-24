/**
 * Copyright 2015 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package mlearning.grupolink.com.mlearningandroid.gcmClasses;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

import com.google.android.gms.gcm.GcmListenerService;

import mlearning.grupolink.com.mlearningandroid.R;
import mlearning.grupolink.com.mlearningandroid.Home.activities.MainActivity;

public class MyGcmListenerService extends GcmListenerService {

    private static final String TAG = "MyGcmListenerService";
    // [START receive_message]
    @Override
    public void onMessageReceived(String from, Bundle data) {
        //String message = data.getString("message");
        String message = data.getString("mensaje");
        String tipo = data.getString("tipo");
        String cursoID = data.getString("idCur");
        /*String codServ = data.getString("codServ");
        String categoryID = data.getString("idCat");*/
        String titulo = data.getString("titulo");

        Log.d(TAG, "From: " + from);
        Log.d(TAG, "Message: " + message);

        if (from.startsWith("/topics/")) {
            // message received from some topic.
        } else {
            // normal downstream message.
        }

        // [START_EXCLUDE]
        int idCurso = 0;
        if(cursoID!=null && !cursoID.equals(""))
            idCurso = Integer.parseInt(cursoID);
        if(tipo==null)
            tipo="";
        if(titulo==null)
            titulo="";
        if(message==null)
            message="";
        sendNotification(message, tipo, idCurso, titulo);
        //sendNotification(message, tipo, Integer.parseInt(encuentroID), titulo);

        //data.fail!!!
        // [END_EXCLUDE]
    }
    // [END receive_message]


    private void sendNotification(String message, String tipo, int idCurso, String titulo) {
        PendingIntent pendingIntent;
        Intent intent;
        //int numeroNotificacion = SharedPreferencesManager.getValorEsperadoInt(getApplicationContext(),"NotificationCount","NotificationNumber");
        //if(numeroNotificacion>100)
        //    numeroNotificacion=0;
        Log.e("tipo", "->" + tipo);
        Log.e("idPartidoNoticia", "->" + idCurso);

        Double d = Math.random() * 1024;
        Integer numeroNotificacion = d.intValue();

            intent = new Intent(this, MainActivity.class);
            intent.putExtra("idCurso", idCurso);
            intent.putExtra("seccion", tipo);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            TaskStackBuilder taskStackBuilder  = TaskStackBuilder.create(this).addParentStack(MainActivity.class).addNextIntent(intent);
            pendingIntent = taskStackBuilder.getPendingIntent(numeroNotificacion, PendingIntent.FLAG_UPDATE_CURRENT);
            //PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);


        Uri urlsound = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.notificacion_campana);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                //.setSmallIcon(R.drawable.icon_vivo)
                .setSmallIcon(R.mipmap.ic_notification)
                .setContentTitle(titulo)
                .setContentText(message)
                .setSound(urlsound)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(numeroNotificacion , notificationBuilder.build());
        //numeroNotificacion++;
        //SharedPreferencesManager.setValorInt(getApplicationContext(),"NotificationCount",numeroNotificacion,"NotificationNumber");
    }
}
