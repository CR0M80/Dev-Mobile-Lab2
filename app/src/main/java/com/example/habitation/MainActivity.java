package com.example.habitation;

import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final double TAXE_PAR_M2    = 2500.0;
    private static final double TAXE_PAR_PIECE = 1000.0;
    private static final double TAXE_PISCINE   = 15000.0;
    private static final double TAXE_ASCENSEUR = 5000.0;
    private static final double TAXE_PROXIMITE = 2000.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText  Surface     = findViewById(R.id.surface);
        EditText  Pieces      = findViewById(R.id.pieces);
        CheckBox  cbPiscine   = findViewById(R.id.cb_piscine);
        CheckBox  cbAscenseur = findViewById(R.id.cb_ascenseur);
        CheckBox  cbEcole     = findViewById(R.id.cb_ecole);
        CheckBox  cbMosquee   = findViewById(R.id.cb_mosquee);
        CheckBox  cbHopital   = findViewById(R.id.cb_hopital);
        CheckBox  cbMarche    = findViewById(R.id.cb_marche);
        Button    btnCalculer = findViewById(R.id.btn_calculer);
        Button    btnReset    = findViewById(R.id.btn_reset);
        TextView  tvResultat  = findViewById(R.id.tv_resultat);

        btnCalculer.setOnClickListener(v -> {
            String sSurface = Surface.getText().toString();
            String sPieces  = Pieces.getText().toString();

            if (sSurface.isEmpty() || sPieces.isEmpty()) {
                Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
                return;
            }

            int nbProx = (cbEcole.isChecked() ? 1 : 0)
                    + (cbMosquee.isChecked() ? 1 : 0)
                    + (cbHopital.isChecked() ? 1 : 0)
                    + (cbMarche.isChecked() ? 1 : 0);

            double total = Double.parseDouble(sSurface) * TAXE_PAR_M2
                    + Integer.parseInt(sPieces)    * TAXE_PAR_PIECE
                    + (cbPiscine.isChecked()   ? TAXE_PISCINE   : 0)
                    + (cbAscenseur.isChecked() ? TAXE_ASCENSEUR : 0)
                    + nbProx                   * TAXE_PROXIMITE;

            tvResultat.setText(String.format("Impôt total : %.2f MAD", total));
        });

        // Rafraîchir — remet tout à zéro
        btnReset.setOnClickListener(v -> {
            Surface.setText("");
            Pieces.setText("");
            cbPiscine.setChecked(false);
            cbAscenseur.setChecked(false);
            cbEcole.setChecked(false);
            cbMosquee.setChecked(false);
            cbHopital.setChecked(false);
            cbMarche.setChecked(false);
            tvResultat.setText("");
            Surface.requestFocus();
        });
    }
}