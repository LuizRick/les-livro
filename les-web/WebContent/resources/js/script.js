
$("#txtEdicao,#txtTitulo").on('focus',function() {
	$(window).scrollTop(0);
});


$("#frmCadastar").on('submit',function(){
	$("input[type='text']").each(function(e,elt){
		
	});
});

$("body").on('focusin',"[data-mask]", () => {
	
});

$("body [data-mask]").each((i,elt) =>{
	var mask = $(elt).attr("data-mask");
	$(elt).mask(mask);
	$(elt).attr("placeholder",mask);
});

$('body').on('focusin', '.input-phone', function() {
    var $this = $(this);
    if( (typeof $this.data()['rawMaskFn'] !== "function") ) {
        // dynamically adds the mask plugin
        $this.mask("(99)-9999-9999"); // probably adds a blur event

        // make sure its the first thing in blur event
        if($this.hasClass('input-cell-phone')) { // ********* moved here so
													// this blur event can get
													// added after the above
													// event

            $('.input-cell-phone').blur(function() {// on() way doesnt work here
													// for some reason
                // if clear cell phone, make sure to clear daytime phone
                var phone_val = $.trim($(this).val());
                if(phone_val==""){
                    // find daytime equivilent and clear too
                    $(this).parents('.container').find('input.input-day-phone').val('');
                }
            });
        }
    }
});




$("#lista_cupons").on('click','.remover',function(){
	$(this).parent().remove();
});


$("#resetar").on('click',function(){
	$("#frmCadastrar input").val("");
});


$(".cardcheck").on('click',function(){
	var index = $(this).attr("data-index");
	var elt = $("[data-cardindex='" + index + "']");
	if($(".input-frete:checked").index() == -1){
		  alert("Selecione um endereco de entrega");
		  $(this).prop("checked",false);
	}
	if(this.checked){
		elt.removeAttr("disabled");
	}else{
		elt.attr("disabled","disabled");
	}
});


$('body').on('change','.cupomcompra',function(){
	
});

$("#descontos").on('change',function(){
	var value = parseFloat($(this).val());
	var total = Number($("#totalPago").val());
});

+function(){
  var app = angular.module("app",[]);
  if(getParameterByName("msg") != "0"){
	  $("#msg").html(`<div class='alert alert-info'>${getParameterByName('msg')}</div>`);
  }
  
  app.controller('FinalizacaoCompraController',['$scope',function($scope){
	  var self = this;
	  this.totalPago = 0;
	  this.total = 0;
	  this.descontos = 0;
	  this.cupons = [];
	  this.cartoes = [];
	  
	  this.setProp = function(prop,value){
		  self[prop] = value;
		  return value;
	  }
	  
	  $(".input-frete").on('click',function(){
		 var isChecked = this.checked;
		 var frete = 0;
		 
		 if(isChecked){
			 var produtos = JSON.parse($("#produtosJSON").val());
			 var item;
			 for(var x in produtos){
				 item = produtos[x];
				 frete += (item.produto.dimensao.peso * 2) / 10;
			 }
		 }
		 $("#txtFrete").val(frete);
		 $("#totalPago").val(function(){
			 var value = parseFloat(this.value);
			 return (value + frete).toFixed(2);
		 });
	  });
	  
	  $("[data-cardindex]").on('change',function(){
		  var value = 0;
		  $("[data-cardindex]:enabled").each(function(i,elt){
			 var num = parseFloat(elt.value); 
			 if(isNaN(num)){
				 num = 0;
				 $(elt).val("0.0");
			 }
			 value += num;
		  });
		  if(isNaN(value))
			  value = 0;
		  var desconto = Number($("#descontos").val());
		  var total = self.total - (value  + desconto);
		  if(total > $("#totalPago").val()){
			  alert("valor + descontos ultrapassa o valor total do pedido");
			  $(this).focus();
		  }
		  else{
			  $('#totalPago').val($("#totalPago").val() - $("#totalPago").val());
		  }
		  $scope.$apply();
	  });
	  
	  $("#btnAddCupom").on('click',function(){
			var Model = {};
			Model.codigoCupom = $("#codigoCupom").val();
			for(var x in self.cupons){
				var item = self.cupons[x];
				if(item.valor && item.codigoCupom == Model.codigoCupom){
					alert("Desconto ja foi aplicado");
					return;
				}
			}
			WebRequestAsync('verificarCupom',Model).then(function(resp){
				var descontos = Number($("#descontos").val());
				var total = Number($("#totalPago").val());
				if(isNaN(descontos))
					descontos = 0;
				if(resp.valor){
					if(total - (descontos + resp.valor) < 0)
						alert("não foi possivel aplicar desconto pois o mesmo passa do necessario para compra tente um cupom de menor valor");
					else{
						descontos += resp.valor;
						$("#descontos").val(descontos);
						self.cupons.push(resp);
						$scope.$apply();
						$("#frmSetCompra").append(`<input type='hidden' name='cupomCompra' value='${resp.codigoCupom}'/>`);
						alert("desconto aplicado com successo");
					}
				}else{
					alert("Cupom não localizado")
				}
			}).catch(function(err,status,xhr){
				
			});
		});
	  
	  $("#frmSetCompra").on('submit',function(e){
		 var obj = {};
		 obj.formasPagamento = [];
		 var form = document.getElementById('frmSetCompra');
		 var value = 0;
		  $("[data-cardindex]:enabled").each(function(i,elt){
			 var num = parseFloat(elt.value);
			 var cartao = {
					 id : $("[data-index]").eq($(elt).attr("data-cardindex")).val(),
					 valor:num,
					 numero:$(elt).parent().parent().parent().find(".numcard").text()
			 };
			 if(isNaN(num)){
				 num = 0;
				 $(elt).val("0.0");
			 }
			 value += num;
			 obj.formasPagamento.push(cartao);
			 $("#cartoesJSON").val(JSON.stringify(obj.formasPagamento));
		  });
		  $("#cuponsJSON").val(JSON.stringify(self.cupons));
		  var jsonEndereco = $(".input-frete:checked").attr("data-json")
		  $("#enderecoJSON").val(jsonEndereco);
		  if(isNaN(value))
			  value = 0;
		  var desconto = Number($("#descontos").val());
		  var total = self.total - (value  + desconto);
		  if(total > (value + desconto)){
			  alert("Cartao(es) + descontos nao abate o valor total da compra");
			  return false;
		  }
	  });
  }]);
  
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

function WebRequestAsync(urlSend,objData){
	return new Promise(function(resolve, reject){
		$.ajax({
			url:urlSend,
			async:true,
			data:objData,
			type:"post",
			dataType: 'json',
			success:function(resp){
				resolve(resp);
			},
			error:function(err,status,xhr){
				reject(err,status,xhr);
			}
		});
	});
}

function WebRequestAsyncJson(urlSend,objData){
	return new Promise(function(resolve, reject){
		$.ajax({
			url:urlSend,
			async:true,
			contentType:"application/json; charset=utf-8",
			data:objData,
			type:"post",
			dataType: 'json',
			success:function(resp){
				resolve(resp);
			},
			error:function(err,status,xhr){
				reject(err,status,xhr);
			}
		});
	});
}

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