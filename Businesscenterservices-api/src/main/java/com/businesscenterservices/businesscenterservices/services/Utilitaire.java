package com.businesscenterservices.businesscenterservices.services;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class Utilitaire {

    public MappingJacksonValue getFilter(Object o, String filterName, String propertyName) {
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.serializeAllExcept(propertyName);

        FilterProvider listDeNosFiltres = new SimpleFilterProvider().addFilter(filterName, filter);

        MappingJacksonValue filtres = new MappingJacksonValue(o);

        filtres.setFilters(listDeNosFiltres);

        return filtres;
    }

    public MappingJacksonValue getFilterList(Object o, String filterName1, String propertyName1, String filterName2, String propertyName2) {
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.serializeAllExcept(propertyName1);
        SimpleBeanPropertyFilter filter2 = SimpleBeanPropertyFilter.serializeAllExcept(propertyName2);

        FilterProvider listDeNosFiltres = new SimpleFilterProvider().addFilter(filterName1, filter).addFilter(filterName2, filter2);

        MappingJacksonValue filtres = new MappingJacksonValue(o);

        filtres.setFilters(listDeNosFiltres);

        return filtres;
    }

    public String generateAlphaNumeriqueRandomString() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 5;
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        return generatedString;
    }

   /*  public String genereNumeroDemande(String date, List<DemandeAchat> demandeAchats) {
        if (demandeAchats == null || demandeAchats.isEmpty()) {
            return date + "000000001";
        } else {
            return date + new DecimalFormat("00000000").format(demandeAchats.size() + 1);
        }
    }

    public String genereNumeroBonCommande(String date, List<BonCommande> bonCommandes) {
        if (bonCommandes == null || bonCommandes.isEmpty()) {
            return date + "00001";
        } else {
            return date + new DecimalFormat("0000").format(bonCommandes.size() + 1);
        }
    }

    public String genereRefBonCommande(String date, List<BonCommande> bonCommandes) {
        if (bonCommandes == null || bonCommandes.isEmpty()) {
            return "BC-" + date + "00001";
        } else {
            return "BC-" + date + new DecimalFormat("0000").format(bonCommandes.size() + 1);
        }
    }

    public String genereRefFournisseur(String date, List<Fournisseur> fournisseurs) {
        if (fournisseurs == null || fournisseurs.isEmpty()) {
            return "FN-" + date + "00001";
        } else {
            return "FN-" + date + new DecimalFormat("0000").format(fournisseurs.size() + 1);
        }
    }

    public String genereRefProduct(String date, List<Produit> produits, Long categorieID) {
        if (produits == null || produits.isEmpty()) {
            return categorieID.toString() + date + "-" + "000000001";
        } else {
            return categorieID.toString() + date + "-" + new DecimalFormat("00000000").format(produits.size() + 1);
        }
    }

    public String genereCipStock(String date, List<Produit> produits, Long categorieID) {
        if (produits == null || produits.isEmpty()) {
            return "CIP-" + categorieID.toString() + date + "-" + "000000001";
        } else {
            return "CIP-" + categorieID.toString() + date + "-" + new DecimalFormat("00000000").format(produits.size() + 1);
        }
    }
*/
   /*  public String generateCodeQrCode(String date, List<QrCode> qrCodes, String codeCategorie) {
        if (qrCodes == null || qrCodes.isEmpty()) {
            return codeCategorie + "-" + date + "-" + "0001";
        } else {
            return codeCategorie + "-" + date + "-" + new DecimalFormat("000").format(qrCodes.size() + 1);
        }
    } */

}