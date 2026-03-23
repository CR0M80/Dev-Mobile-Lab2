# 🏠 Impôts Locaux App — By SAAD

Application Android de calcul des impôts locaux d'un bien immobilier.

## 📽️ Démonstration

https://github.com/user-attachments/assets/12e7bae0-b39d-4038-873c-a73783967927

## 🖼️ Interface — `res/layout/activity_main.xml`

### Titre — `TextView`

```xml
<TextView
    android:text="Impôts Locaux — By SAAD"
    android:textColor="#1B7A3E"
    android:textSize="18sp"
    android:textStyle="bold"/>
```
Étiquette en haut de l'écran.

### Champs de saisie — `EditText`

```xml
<EditText
    android:id="@+id/surface"
    android:hint="Surface (m²)"
    android:inputType="numberDecimal"
    android:backgroundTint="#A5D6A7"/>

<EditText
    android:id="@+id/pieces"
    android:hint="Nombre de pièces"
    android:inputType="number"
    android:backgroundTint="#A5D6A7"/>
```

| Attribut | Rôle |
|---|---|
| `inputType="numberDecimal"` | Ouvre un clavier numérique avec virgule pour la surface |
| `inputType="number"` | Ouvre un clavier numérique entier pour les pièces |
| `backgroundTint="#A5D6A7"` | Soulignement vert clair sous le champ |

### Checkboxes en 3 colonnes — `LinearLayout` horizontal

```xml
<LinearLayout
    android:orientation="horizontal">

    <CheckBox android:id="@+id/cb_piscine"
        android:layout_width="0dp"
        android:layout_weight="1"
        android:text="Piscine"/>

    <CheckBox android:id="@+id/cb_ascenseur"
        android:layout_width="0dp"
        android:layout_weight="1"
        android:text="Ascenseur"/>

    <CheckBox android:id="@+id/cb_ecole"
        android:layout_width="0dp"
        android:layout_weight="1"
        android:text="École"/>
</LinearLayout>
```

La combinaison `layout_width="0dp"` + `layout_weight="1"` sur chaque `CheckBox` divise la ligne en **3 colonnes égales** automatiquement, quelle que soit la taille de l'écran. Deux lignes de 3 cases couvrent les 6 critères : piscine, ascenseur, école, mosquée, hôpital, marché.

### Boutons Calculer / Rafraîchir

```xml
<LinearLayout android:orientation="horizontal">

    <Button android:id="@+id/btn_calculer"
        android:layout_weight="1"
        android:text="Calculer"
        android:backgroundTint="#2E7D32"/>

    <Button android:id="@+id/btn_reset"
        android:layout_weight="1"
        android:text="Rafraîchir"
        android:backgroundTint="#A5D6A7"/>
</LinearLayout>
```

Les deux boutons utilisent également `layout_weight="1"` pour occuper chacun la moitié de la largeur. Le bouton Calculer est vert foncé, le bouton Rafraîchir est vert clair pour les distinguer visuellement.

### Résultat — `TextView` (tv_resultat)

```xml
<TextView
    android:id="@+id/tv_resultat"
    android:textColor="#1B7A3E"
    android:textSize="20sp"
    android:textStyle="bold"/>
```

Vide au démarrage. Mis à jour dynamiquement depuis le Java avec le montant calculé en MAD.

---

## ☕ Logique — `MainActivity.java`

### Imports nécessaires

```java
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
```

L'import `android.widget.*` regroupe en une ligne tous les widgets utilisés : `Button`, `CheckBox`, `EditText`, `TextView`, `Toast`.

### Constantes du barème fiscal

```java
private static final double TAXE_PAR_M2    = 2500.0;
private static final double TAXE_PAR_PIECE = 1000.0;
private static final double TAXE_PISCINE   = 15000.0;
private static final double TAXE_ASCENSEUR = 5000.0;
private static final double TAXE_PROXIMITE = 2000.0;
```

Toutes les valeurs fiscales sont déclarées comme constantes `private static final` en haut de la classe.

### Validation des champs

```java
if (sSurface.isEmpty() || sPieces.isEmpty()) {
    Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
    return;
}
```

Si l'un des deux champs texte est vide, un Toast (ce qu on a fait dans le premier lab) d'avertissement s'affiche et le calcul est interrompu avec `return`.

### Comptage des services de proximité

```java
int nbProx = (cbEcole.isChecked()   ? 1 : 0)
           + (cbMosquee.isChecked() ? 1 : 0)
           + (cbHopital.isChecked() ? 1 : 0)
           + (cbMarche.isChecked()  ? 1 : 0);
```

Chaque case cochée ajoute `1` au compteur `nbProx`. Ce total est ensuite multiplié par `TAXE_PROXIMITE`, ce qui évite de répéter la même logique quatre fois.

### Calcul du total

```java
double total = Double.parseDouble(sSurface) * TAXE_PAR_M2
             + Integer.parseInt(sPieces)    * TAXE_PAR_PIECE
             + (cbPiscine.isChecked()   ? TAXE_PISCINE   : 0)
             + (cbAscenseur.isChecked() ? TAXE_ASCENSEUR : 0)
             + nbProx                   * TAXE_PROXIMITE;

tvResultat.setText(String.format("Impôt total : %.2f MAD", total));
```

Le total est calculé en une seule expression. `String.format("%.2f", total)` formate le résultat avec exactement 2 décimales avant de l'afficher dans le `TextView`.

### Bouton Rafraîchir

```java
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
```

Remet tous les champs à leur état initial sans relancer l'application. `requestFocus()` replace le curseur directement sur le champ Surface pour un nouveau calcul immédiat.

---

*Projet réalisé dans le cadre d'un apprentissage Android — SAAD AMAR*
