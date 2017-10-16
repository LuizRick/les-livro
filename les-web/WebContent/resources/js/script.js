$("#txtEdicao,#txtTitulo").on('focus',() => {
	$(window).scrollTop(0);
});


$("#frmCadastar").on('submit',function(){
	$("input[type='text']").each(function(e,elt){
		
	});
});

$("body").on('focusin',"[data-mask]", () => {
	
});

$("[data-mask]").each((i,elt) =>{
	var mask = $(elt).attr("data-mask");
	console.dir(elt);
	$(elt).mask(mask);
	$(elt).attr("placeholder",mask);
});

$('body').on('focusin', '.input-phone', function() {
    var $this = $(this);
    if( (typeof $this.data()['rawMaskFn'] !== "function") ) {
        //dynamically adds the mask plugin
        $this.mask("(99)-9999-9999"); //probably adds a blur event

        //make sure its the first thing in blur event
        if($this.hasClass('input-cell-phone')) { //********* moved here so this blur event can get added after the above event

            $('.input-cell-phone').blur(function() {//on() way doesnt work here for some reason
                //if clear cell phone, make sure to clear daytime phone
                var phone_val = $.trim($(this).val());
                if(phone_val==""){
                    //find daytime equivilent and clear too
                    $(this).parents('.container').find('input.input-day-phone').val('');
                }
            });
        }
    }
});


$("#resetar").on('click',function(){
	$("#frmCadastrar input").val("");
});

+function(){
  var app = angular.module("app",[]);
  app.controller('FormClienteController',['$scope', function($scope){
        this.tab = "tab1";
        this.telefones = [1];
        this.cartoes = [1];
        this.enderecos = [1];
        this.setTab = (tab) => {
        	this.tab = tab;
        }
        
        this.addTelefone = () => {
        	this.telefones.push(this.telefones.length + 1);
        };
        
        this.addCartao = () => {
        	this.cartoes.push(this.cartoes.length + 1);
        }
        
        this.removeCartao = () => {
        	if(this.cartoes.length > 1)
        		this.cartoes.pop();
        	else
        		alert("E necessario ao menos um cartão");
        }
        
        this.removeTelefone = (key) =>{
        	if(this.telefones.length > 1)
        		this.telefones.pop();
        	else
        		alert("E necessario ao menos um telefone(voce nao pode remover)");
        }
        
        this.addEndereco  = () => {
        	this.enderecos.push(this.enderecos.length + 1);
        }
        
        this.removeEndereco = () => {
        	if(this.enderecos.length > 1)
        		this.enderecos.pop();
        	else
        		alert("E necessario ao menos um endereco");
        }
        
        this.valueOperacao = function(){
        	if(getParameterByName('id') == 0){
        		return "SALVAR";
        	}else{
        		return "ALTERAR";
        	}
        }
        this.getParameterUrl = getParameterByName;
  }]);
}();

function loadPage(url){
	window.location = url;
}

function getParameterByName(name, url) {
    if (!url) url = window.location.href;
    name = name.replace(/[\[\]]/g, "\\$&");
    var regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"),
        results = regex.exec(url);
    if (!results) return '0';
    if (!results[2]) return '0';
    return decodeURIComponent(results[2].replace(/\+/g, " "));
}