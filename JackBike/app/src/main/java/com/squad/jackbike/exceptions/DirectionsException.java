package com.squad.jackbike.exceptions;

import android.app.Activity;

public class DirectionsException extends AndroidException{

    public DirectionsException (Activity context) {
        super(context, "Erreur: Impossible de trouver une route entre ces deux positions");
    }

}
