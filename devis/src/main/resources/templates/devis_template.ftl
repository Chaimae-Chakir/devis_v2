<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8"/>
    <title>Devis ${devis.numero}</title>
    <link rel="stylesheet" href="classpath:/static/css/styles.css"/>
</head>
<body>

<div class="page">
    <div class="header-container">
        <div class="logo-container">
            <img src="classpath:/static/images/logo.png" alt="Logo Agilisys"/>
        </div>
        <div class="info-container">
            <div class="info-line">ICE: 001568437000073</div>
            <div class="info-line">www.agilisys.ma</div>
            <div class="info-line">ayoubelabbassi@agilisys.ma</div>
            <div class="info-line">0619826933</div>
        </div>
    </div>

    <div class="main-content">
        <div>
            <div class="section-title">Prestation</div>
            <div class="client-ice">
                LOGO client
            </div>
            <table class="devis-info-table">
                <tr>
                    <td class="label">Numéro du devis</td>
                    <td>${devis.numero}</td>
                    <td class="label">${devis.client.nom}</td>
                </tr>
                <tr>
                    <td class="label">Date devis</td>
                    <td>${devis.dateCreation}</td>
                    <td class="label">${devis.client.adresse}</td>
                    <td></td>
                </tr>
                <tr>
                    <td class="label">Montant du devis</td>
                    <td>${devis.totalHt} HT</td>
                    <td></td>
                </tr>
            </table>

            <div class="client-ice">
                ICE : ${devis.client.ice}
            </div>
        </div>

        <div class="section-title">Prestation</div>
        <p class="bold-italic">Lorem Ipsum</p>

        <div class="section-title">Périmètre Fonctionnel</div>
        <p>${devis.meta.perimetre}</p>
        <p>
            Le <span class="bold-italic">Lorem Ipsum</span> est simplement du faux texte employé dans la composition et
            la mise
            en page avant impression. Le Lorem Ipsum est le faux texte standard de l'imprimerie depuis les années 1500,
            quand un imprimeur anonyme assembla ensemble des morceaux de texte pour réaliser un livre spécimen de
            polices de texte. Il n'a pas fait que survivre cinq siècles, mais s'est aussi adapté à la bureautique
            informatique, sans que son contenu n'en soit modifié. Il a été popularisé dans les années 1960 grâce à
            la vente de feuilles Letraset contenant des passages du Lorem Ipsum, et, plus récemment, par son
            inclusion dans des applications de mise en page de texte, comme Aldus PageMaker.
        </p>
        <p>Le passage de Lorem Ipsum standard, utilisé depuis 1500</p>
        <p class="bold">"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor
            incididunt ut
            labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco
            laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in
            voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non
            proident, sunt in culpa qui officia deserunt mollit anim id est laborum."</p>
        <p>
            <span class="highlight">Section 1.10.32 du "De Finibus Bonorum et Malorum" de Ciceron (45 av. J.-C.)</span>
        </p>
        <p>
            "Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium,
            totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt
            explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia
            consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui
        </p>
    </div>

    <div class="footer">
        AGILISYS SARL AU Capital de 100 000.00 DH | ICE : 001568437000073 | Patente : 25728214 | IF : 1372126 | RC :
        105883 | CNSS : 4418114 | Tél : 0619826933 | Adresse : 15 avenue ALABTAL appartement 4, 4ème étage Rabat Maroc
    </div>
</div>

<div class="page">
    <div class="main-content">
        <p> dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora
            incidunt ut labore et dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, quis
            nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur?
            Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur,
            vel illum qui dolorem eum fugiat quo voluptas nulla pariatur?"
        </p>
        <p>Traduction de H. Rackham (1914)
            "But I must explain to you how all this mistaken idea of denouncing pleasure and praising pain was born
            and I will give you a complete account of the system, and expound the actual teachings of the great </p>
        <p>
            explorer of the truth, the master-builder of human happiness. No one rejects, dislikes, or avoids pleasure
            itself, because it is pleasure, but because those who do not know how to pursue pleasure rationally
            encounter consequences that are extremely painful. Nor again is there anyone who loves or pursues or
            desires to obtain pain of itself, because it is pain, but because occasionally circumstances occur in which
            toil and pain can procure him some great pleasure. To take a trivial example, which of us ever undertakes
            laborious physical exercise, except to obtain some advantage from it? But who has any right to find fault
            with a man who chooses to enjoy a pleasure that has no annoying consequences, or one who avoids a
            pain that produces no resultant pleasure?"
        </p>

        <div class="section-title">Planning</div>
        <div class="image-fixed-container">
            <img src="classpath:/static/images/planning.jpg" alt="Planning de projet"/>
        </div>
        <p> ${devis.meta.planning}
            que survivre cinq siècles, mais s'est aussi adapté à la bureautique informatique, sans que son contenu
            n'en soit modifié. Il a été popularisé dans les années 1960 grâce à la vente de feuilles Letraset
            contenant des passages du Lorem Ipsum, et, plus récemment, par son inclusion dans des applications
            de mise en page de texte, comme Aldus PageMaker
        </p>
    </div>
    <div class="section-title">Architecture</div>
    <div class="footer">
        AGILISYS SARL AU Capital de 100 000.00 DH | ICE : 001651414100019 | Patente : 25728214 | IF : 1372126 | RC :
        105883 | CNSS : 4418114 | Tél : 0619826933 | Adresse : 15 avenue ALABTAL appartement 4, 4ème étage Rabat Maroc
    </div>
</div>

<div class="page">
    <div class="main-content">
        <div class="image-fixed-container">
            <img src="classpath:/static/images/architecture.png" alt="Architecture fonctionnelle"/>
        </div>
        <p>
            que survivre cinq siècles, mais s'est aussi adapté à la bureautique informatique, sans que son contenu
            n'en soit modifié. Il a été popularisé dans les années 1960 grâce à la vente de feuilles Letraset
            contenant des passages du Lorem Ipsum, et, plus récemment, par son inclusion dans des applications
            de mise en page de texte, comme Aldus PageMaker
        </p>
        <div class="section-title">Estimation de charge</div>
        <table class="data-table">
            <thead>
            </thead>
            <tbody>
            <tr>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
            </tr>
            <tr>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
            </tr>
            <tr>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
            </tr>
            </tbody>
        </table>

        <div class="section-title">Offre financière et Conditions</div>
        <table class="data-table">
            <thead>
            </thead>
            <tbody>
            <tr>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
            </tr>
            <tr>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
            </tr>
            <tr>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
            </tr>
            <tr>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
            </tr>
            </tbody>
        </table>

        <div class="text-right bold">
            Le 24/03/2023
        </div>
    </div>

    <div class="footer">
        AGILISYS SARL AU Capital de 100 000.00 DH | ICE : 001568437000073 | Patente : 25728214 | IF : 1372126 | RC :
        105883 | CNSS : 4418114 | Tél : 0619826933 | Adresse : 15 avenue ALABTAL appartement 4, 4ème étage Rabat Maroc
    </div>
</div>

<div class="page">
    <div class="main-content">
    </div>

    <div class="footer">
        AGILISYS SARL AU Capital de 100 000.00 DH | ICE : 001568437000073 | Patente : 25728214 | IF : 1372126 | RC :
        105883 | CNSS : 4418114 | Tél : 0619826933 | Adresse : 15 avenue ALABTAL appartement 4, 4ème étage Rabat Maroc
    </div>
</div>

</body>
</html>